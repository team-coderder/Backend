package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.model.*;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.coderder.colorMeeting.exception.ErrorCode.*;
import static com.coderder.colorMeeting.model.TeamRole.LEADER;

@Service
class TeamServiceImpl extends CommonService implements TeamService {

    public TeamServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository, TeamMemberRepository teamMemberRepository, InvitationRepository invitationRepository) {
        super(teamRepository, memberRepository, teamMemberRepository, invitationRepository);
    }

    @Override
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
        return new TeamSimpleResponseDto(newTeam);
    }

    @Override
    public TeamDetailResponseDto showTeamInfo(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 1. TeamMembers라는 객체에서 각 멤버들에 대한 정보를 추출하여 TeamMemberDto 리스트에 담기
        List<TeamMember> teamMembers = team.getTeamMemberList();
        List<TeamMemberDto> teamMemberDtos = buildTeamMemberDtos(teamMembers);

        // 2. 초대장 목록 불러와서 각 초대장 정보를 Dto 리스트에 담기
        List<Invitation> invitations = invitationRepository.findAllByFromTeam(team);
        List<InvitationDto> invitationDtos = buildInvitationDtos(invitations);

        // 3. responseDto 빌드하기
        return TeamDetailResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .myRole(myInfo.getTeamRole().toString())
                .teamMembers(teamMemberDtos)
                .invitations(invitationDtos)
                .build();
    }

    @Override
    public TeamSimpleResponseDto updateTeam(PrincipalDetails userDetails, Long teamId, TeamRequestDto requestDto) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 0. 유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkLeaderRole(myInfo);

        // 1. 그룹의 이름 수정
        team.updateName(requestDto.getName());

        // 2. response 빌드하기
        return new TeamSimpleResponseDto(team);
    }

    @Override
    public ResponseMessage deleteTeam(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 0. 유저가 해당 그룹의 LEADER가 아닐 경우 예외처리
        checkLeaderRole(myInfo);

        // 1. 그룹 삭제하기
        teamRepository.delete(team);

        // 2. 해당 그룹에 대한 멤버 정보도 삭제하기
        teamMemberRepository.deleteAllByTeam(team);

        // 3. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + team.getId() + ") 삭제 완료");
    }

    @Override
    public TeamMembersResponseDto getTeamMembers(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team team = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, team);

        // 1. TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하여 Dto에 담기
        List<TeamMember> teamMembers = team.getTeamMemberList();
        List<TeamMemberDto> teamMemberDtos = buildTeamMemberDtos(teamMembers);

        // 2. response 빌드하기
        return TeamMembersResponseDto.builder()
                .teamMembers(teamMemberDtos)
                .build();
    }

    @Override
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
        return new ResponseMessage("그룹(id : " + targetTeam.getId() + ")의 멤버로 추가 완료");
    }

    @Override
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
        return new ResponseMessage("그룹(id : " + targetTeam.getId() +")에서 멤버 " + cnt + "명 탈퇴 처리 완료");
    }

    @Override
    public TeamResponseDto getMyTeams(PrincipalDetails userDetails) {

        Member me = userDetails.getMember();

        // 1. 나의 그룹들을 추출하여 Dto에 넣기
        List<TeamMember> teamMembers = teamMemberRepository.getAllByMember(me);
        List<TeamSimpleResponseDto> data = buildTeamSimpleResponseDto(teamMembers);

        // 2. response 생성 및 출력하기
        return TeamResponseDto.builder().teams(data).build();
    }

    @Override
    public ResponseMessage leaveTeam(PrincipalDetails userDetails, Long teamId) {

        Member me = userDetails.getMember();
        Team targetTeam = findTeam(teamId);
        TeamMember myInfo = findTeamMember(me, targetTeam);

        // 0. 내가 리더라면, 팀에서 탈퇴할 수 없도록 함
        if (myInfo.getTeamRole() == LEADER) {
            throw new ForbiddenException(INVALID_TEAMROLE_FOR_THIS_REQUEST);
        }

        // 1. TeamMember 삭제하기
        teamMemberRepository.delete(myInfo);


        // 3. response 생성 및 출력하기
        return new ResponseMessage("그룹(id : " + targetTeam.getId() +")에서 탈퇴 완료");
    }

    private List<TeamMemberDto> buildTeamMemberDtos(List<TeamMember> teamMembers) {
        List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Member member = teamMember.getMember();
            teamMemberDtos.add(TeamMemberDto.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .teamRole(String.valueOf(teamMember.getTeamRole()))
                    .build());
        }
        return teamMemberDtos;
    }

    private List<InvitationDto> buildInvitationDtos(List<Invitation> invitations) {
        List<InvitationDto> invitationDtos = new ArrayList<>();
        for (Invitation invitation : invitations) {
            invitationDtos.add(InvitationDto.builder()
                    .id(invitation.getId())
                    .team(new TeamSimpleResponseDto(invitation.getFromTeam()))
                    .fromMember(new MemberDto(invitation.getFromLeader()))
                    .toMember(new MemberDto(invitation.getToMember()))
                    .createdAt(invitation.getCreatedAt())
                    .build()
            );
        }
        return invitationDtos;
    }

    private List<TeamSimpleResponseDto> buildTeamSimpleResponseDto(List<TeamMember> teamMembers) {
        List<TeamSimpleResponseDto> data = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Team team = teamMember.getTeam();
            data.add(new TeamSimpleResponseDto(team));
        }
        return data;
    }

}