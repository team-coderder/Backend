package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.*;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.coderder.colorMeeting.exception.ErrorCode.*;
import static com.coderder.colorMeeting.model.TeamRole.LEADER;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final InvitationRepository invitationRepository;

    @Transactional
    public TeamSimpleResponseDto createTeam(PrincipalDetails userDetails, TeamRequestDto requestDto) {

        // 0. request 오류시 예외처리
        if (requestDto.getName() == null || requestDto.getName().equals(" ")) {
            throw new BadRequestException(INVALID_TEAM_NAME);
        }

        // 1. 팀 생성하기
        Team newTeam = Team.builder()
                .name(requestDto.getName())
                .teamMemberList(new ArrayList<>())
                .teamScheduleList(new ArrayList<>())
                .build();
        teamRepository.save(newTeam);

        // 2. 생성한 팀에 나를 첫번째 멤버로 추가하기
        Member me = userDetails.getMember();
        TeamMember firstTeamMember = TeamMember.builder()
                .team(newTeam)
                .member(me)
                .teamRole(LEADER)
                .build();
        teamMemberRepository.save(firstTeamMember);

        // 3. 응답 생성하기
        TeamSimpleResponseDto response = TeamSimpleResponseDto.builder()
                .teamId(newTeam.getId())
                .name(newTeam.getName())
                .build();
        return response;
    }

    @Transactional
    public TeamDetailResponseDto showTeamInfo(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 1. TeamMembers라는 객체에서 각 멤버들에 대한 정보를 추출하여 TeamMemberDto 리스트에 담기
        List<TeamMember> teamMembers = team.getTeamMemberList();
        List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Member member = teamMember.getMember();
            teamMemberDtos.add(TeamMemberDto.builder()
                    .memberId(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .teamRole(String.valueOf(teamMember.getTeamRole()))
                    .build());
        }
        // 2. 초대장 목록 불러와서 각 초대장 정보를 Dto 리스트에 담기
        List<Invitation> invitations = invitationRepository.findAllByFromTeam(team);
        List<InvitationDto> invitationDtos = new ArrayList<>();
        for (Invitation invitation : invitations) {
            invitationDtos.add(InvitationDto.builder()
                    .invitationId(invitation.getId())
                    .fromTeamId(invitation.getFromTeam().getId())
                    .fromMemberId(invitation.getFromLeader().getId())
                    .toMemberId(invitation.getToMember().getId())
                    .createdAt(invitation.getCreatedAt())
                    .build()
                    );
        }


        // 3. responseDto 빌드하기
        TeamDetailResponseDto response = TeamDetailResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .teamMembers(teamMemberDtos)
                .invitations(invitationDtos)
                .build();
        return response;
    }

    @Transactional
    public TeamSimpleResponseDto updateTeam(PrincipalDetails userDetails, Long teamId, TeamRequestDto requestDto) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 0. 유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkLeaderRole(myInfo);

        // 1. 그룹의 이름 수정
        team.updateName(requestDto.getName());

        // 2. response 빌드하기
        TeamSimpleResponseDto response = TeamSimpleResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .build();
        return response;
    }

    @Transactional
    public ResponseMessage deleteTeam(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 0. 유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkLeaderRole(myInfo);

        // 1. 그룹 삭제하기
        teamRepository.delete(team);

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(teamId : " + team.getId() + ") 삭제 완료");
    }

    @Transactional
    public TeamMembersResponseDto getTeamMembers(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 1. TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하여 Dto에 담기
        List<TeamMember> teamMembers = team.getTeamMemberList();
        List<TeamMemberDto> members = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Member member = teamMember.getMember();
            TeamMemberDto teamMemberDto = TeamMemberDto.builder()
                    .memberId(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .teamRole(String.valueOf(teamMember.getTeamRole()))
                    .build();
            members.add(teamMemberDto);
        }

        // 2. response 빌드하기
        TeamMembersResponseDto response = TeamMembersResponseDto.builder()
                .teamMembers(members)
                .build();
        return response;
    }

    @Transactional
    public ResponseMessage addMember(PrincipalDetails userDetails, TeamMemberRequestDto requestDto) {

        Team targetTeam = findTeam(requestDto.getTeamId());
        Member me = userDetails.getMember();
        TeamMember myInfo = findTeamMember(me, targetTeam);
        Member targetMember = findMember(requestDto.getMemberIds().get(0));

        // 0. 예외처리
        checkLeaderRole(myInfo);                        //  유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkTeamMember(targetMember, targetTeam);      // 대상 유저가 이미 requestDto의 Team에 속해있는 경우

        // 1. 해당 그룹에 유저 추가하기
        TeamMember teamMember = TeamMember.builder()
                .member(targetMember)
                .team(targetTeam)
                .teamRole(TeamRole.FOLLOWER)
                .build();
        teamMemberRepository.save(teamMember);

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(teamId : " + targetTeam.getId() + ")의 멤버로 추가 완료");
    }

    public ResponseMessage memberOut(PrincipalDetails userDetails, TeamMemberRequestDto requestDto) {

        Member me = userDetails.getMember();
        Team targetTeam = findTeam(requestDto.getTeamId());
        TeamMember myInfo = findTeamMember(me, targetTeam);

        // 0. 유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkLeaderRole(myInfo);

        // 1. 해당 그룹에서 멤버들 제외하기
        List<Long> memberIds = requestDto.getMemberIds();
        int cnt = 0;
        for (Long memberId : memberIds) {
            Member targetMemer = findMember(memberId);
            TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(targetMemer, targetTeam);
            teamMemberRepository.delete(teamMember);
            cnt++;
        }

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(teamId : " + targetTeam.getId() +")에서 멤버 " + cnt + "명 탈퇴 처리 완료");
    }

    public TeamResponseDto getMyTeams(PrincipalDetails userDetails) {

        Member me = userDetails.getMember();

        // 1. 나의 그룹들을 추출하여 Dto에 넣기
        List<TeamSimpleResponseDto> data = new ArrayList<>();
        List<TeamMember> teamMembers = teamMemberRepository.getAllByMember(me);
        for (TeamMember teamMember : teamMembers) {
            Team team = teamMember.getTeam();
            data.add(TeamSimpleResponseDto.builder()
                    .teamId(team.getId())
                    .name(team.getName())
                    .build());
        }
        TeamResponseDto response = TeamResponseDto.builder().teams(data).build();

        // 2. response 생성 및 출력하기
        return response;
    }

    public ResponseMessage leaveTeam(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team targetTeam = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, targetTeam);

        // 1. TeamMember 삭제하기
        teamMemberRepository.delete(myInfo);

        // 2. response 생성 및 출력하기
        return new ResponseMessage("그룹(teamId : " + targetTeam.getId() +")에서 탈퇴 완료");
    }

    private Team findTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
        return team;
    }

    private Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
        return member;
    }

    private TeamMember findTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember == null) {
            throw new NotFoundException(TEAM_MEMBER_NOT_FOUND);
        }
        return teamMember;
    }

    private void checkLeaderRole(TeamMember teamMember) {
        if (teamMember.getTeamRole() != LEADER) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }

    private void checkTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember != null) {
            throw new BadRequestException(ALREADY_TEAM_MEMBER);
        }
    }

    private void checkSameMember(Member member1, Member member2) {
        if (member1.getId() != member2.getId()) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }
}