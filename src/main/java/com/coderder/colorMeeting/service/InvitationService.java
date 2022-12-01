package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.response.ResponseMessage;
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
import java.util.List;
import java.util.Optional;

import static com.coderder.colorMeeting.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final InvitationRepository invitationRepository;

    public ResponseMessage inviteMember(TeamMemberRequestDto requestDto) {

        Team team = isPresentTeam(requestDto.getTeamId());
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }

        List<Long> memberIds = requestDto.getMemberIds();
        int cnt = 0;
        for (Long memberId : memberIds) {

            Member member = isPresentMember(memberId);
            if (member == null) {
                throw new NotFoundException(MEMBER_NOT_FOUND);
            }

            TeamMember teamMember = isPresentTeamMember(member, team);
            if (teamMember != null) {
                throw new BadRequestException(ALREADY_TEAM_MEMBER);
            }

            Invitation invitation = invitationDoubleCheck(member, team);
            if (invitation != null) {
                throw new BadRequestException(ALREADY_INVITED);
            }

            // 회원가입 구현 전까지 member_id = 1인 유저로 하드코딩
            Member me = isPresentMember(1L);
            TeamMember myRole = isPresentTeamMember(me, team);
            if (myRole == null) {
                throw new BadRequestException(TEAM_MEMBER_NOT_FOUND);
            }
            if (myRole.getTeamRole() != TeamRole.LEADER) {
                throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
            }

            Invitation new_invitation = Invitation.builder()
                    .fromTeam(team)
                    .fromLeader(me)
                    .toMember(member)
                    .createdAt(LocalDateTime.now())
                    .build();

            invitationRepository.save(new_invitation);

            cnt++;
        }
        return new ResponseMessage("그룹(teamId : " + team.getId() +")에 멤버 " + cnt + "명 초대 완료");
    }

    public ResponseMessage acceptInvitation(Long invitationId) {

        Invitation invitation = isPresentInvitation(invitationId);
        if (invitation == null) {
            throw new NotFoundException(INVITATION_NOT_FOUND);
        }

        Team team = isPresentTeam(invitation.getFromTeam().getId());
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }

        Member member = isPresentMember(invitation.getToMember().getId());
        if (member == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

//        // 나한테 보낸 초대장이 아닐 경우
//        if (invitation.getToMember() != me) {
//            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
//        }

        TeamMember teamMember = TeamMember.builder()
                .team(invitation.getFromTeam())
                .member(invitation.getToMember())
                .teamRole(TeamRole.FOLLOWER)
                .build();

        teamMemberRepository.save(teamMember);
        invitationRepository.delete(invitation);

        return new ResponseMessage("그룹(teamId : " + invitation.getFromTeam().getId() +")의 초대장 수락 완료");
    }

    private Team isPresentTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team.orElse(null);
    }

    private Member isPresentMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElse(null);
    }

    private TeamMember isPresentTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        return teamMember;
    }

    private Invitation invitationDoubleCheck(Member member, Team team) {
        Invitation invitation = invitationRepository.findByToMemberAndFromTeam(member, team);
        return invitation;
    }

    private Invitation isPresentInvitation(Long invitationId) {
        Optional<Invitation> invitation = invitationRepository.findById(invitationId);
        return invitation.orElse(null);
    }
}
