package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.*;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.coderder.colorMeeting.exception.ErrorCode.*;
import static com.coderder.colorMeeting.model.TeamRole.*;

@Service
public class InvitationServiceImpl extends CommonService implements InvitationService {


    public InvitationServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository, TeamMemberRepository teamMemberRepository, InvitationRepository invitationRepository) {
        super(teamRepository, memberRepository, teamMemberRepository, invitationRepository);
    }

    @Override
    public InvitationListDto showAllInvitations(PrincipalDetails userDetails) {

        Member me = userDetails.getMember();

        // 1. 나의 초대장 목록을 불러오기
        List<Invitation> invitations = invitationRepository.findAllByToMember(me);

        // 2. 나의 초대장들을 하나씩 추출하여 Dto에 넣기
        List<InvitationDto> data = buildInvitationDtos(invitations);

        // 3. response 출력하기
        return InvitationListDto.builder()
                .invitations(data)
                .build();
    }

    @Override
    public ResponseMessage createInvitation(PrincipalDetails userDetails, TeamMemberRequestDto requestDto) {

        Member me = userDetails.getMember();
        Team targetTeam = findTeam(requestDto.getTeamId());
        TeamMember myInfo = findTeamMember(me, targetTeam);

        // 0. 예외처리
        checkLeaderRole(myInfo);    // 유저가 해당 팀의 리더가 아닐 경우 오류 발생
        if (requestDto.getMemberIds().contains(me.getId())) {    // 초대장을 보내는 상대가 나 자신일 경우 오류 발생
            throw new BadRequestException(CANNOT_INVITE_ONESELF);
        }

        // 1. 초대장 생성하기
        List<Long> memberIds = requestDto.getMemberIds();
        int cnt = 0;
        for (Long memberId : memberIds) {
            Member targetMember = findMember(memberId);
            checkInvitation(targetMember, targetTeam);      // 예외처리 : 이미 초대장이 있다면 오류 발생
            Invitation new_invitation = Invitation.builder()
                    .fromTeam(targetTeam)
                    .fromLeader(me)
                    .toMember(targetMember)
                    .createdAt(LocalDateTime.now())
                    .build();
            invitationRepository.save(new_invitation);
            cnt++;
        }

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + targetTeam.getId() +")에 멤버 " + cnt + "명 초대 완료");
    }

    @Override
    public ResponseMessage acceptInvitation(PrincipalDetails userDetails, Long invitationId) {

        Member me = userDetails.getMember();
        Invitation invitation = findInvitation(invitationId);
        Team targetTeam = findTeam(invitation.getFromTeam().getId());
        Member targetMember = findMember(invitation.getToMember().getId());

        // 0. 예외처리 : 초대장 속 member가 유저 본인이 아니라면 예외 발생
        checkSameMember(me, targetMember);

        // 1. TeamMember 생성함으로서 그룹에 멤버 추가하기
        TeamMember teamMember = TeamMember.builder()
                .team(targetTeam)
                .member(targetMember)
                .teamRole(FOLLOWER)
                .build();
        teamMemberRepository.save(teamMember);

        // 2. 초대장 삭제하기
        invitationRepository.delete(invitation);

        // 3. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + invitation.getFromTeam().getId() +")의 초대장 수락 완료");
    }

    @Override
    public ResponseMessage refuseInvitation(PrincipalDetails userDetails, Long invitationId) {

        Member me = userDetails.getMember();
        Invitation invitation = findInvitation(invitationId);
        Member targetMember = findMember(invitation.getToMember().getId());

        // 0. 예외처리 : 초대장 속 member가 유저 본인이 아니라면 예외 발생
        checkSameMember(me, targetMember);

        // 1. 초대장 삭제하기
        invitationRepository.delete(invitation);

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + invitation.getFromTeam().getId() +")의 초대장 거절 완료");
    }

    @Override
    public ResponseMessage cancelInvitation(PrincipalDetails userDetails, Long invitationId) {

        Member me = userDetails.getMember();
        Invitation invitation = findInvitation(invitationId);
        Member fromMember = findMember(invitation.getFromLeader().getId());

        // 0. 예외처리 : 초대장을 보낸 유저 본인이 아니라면 예외 발생
        checkSameMember(me, fromMember);

        // 1. 초대장 삭제하기
        invitationRepository.delete(invitation);

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + invitation.getFromTeam().getId() +")의 초대장 회수 완료");
    }
}