package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.*;
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
        newTeam.addTeamMember(firstTeamMember);

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
        Team team = isPresentTeam(teamId);
        TeamMember myInfo = isPresentTeamMember(me, team);

        // 1. TeamMembers라는 객체에서 각 멤버들에 대한 정보를 추출하여 TeamMemberDto 리스트에 담기
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

        // 2. responseDto 빌드하기
        TeamDetailResponseDto response = TeamDetailResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .teamMembers(members)
                .invitations(null)
                .build();
        return response;
    }

    @Transactional
    public TeamSimpleResponseDto updateTeam(PrincipalDetails userDetails, Long teamId, TeamRequestDto requestDto) {

        Member me = userDetails.getMember();
        Team team = isPresentTeam(teamId);
        TeamMember myInfo = isPresentTeamMember(me, team);

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
        Team team = isPresentTeam(teamId);
        TeamMember myInfo = isPresentTeamMember(me, team);

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
        Team team = isPresentTeam(teamId);
        TeamMember myInfo = isPresentTeamMember(me, team);

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

    // 그룹에 유저 추가하기 - Member 구현 완료 후 구현 가능
    @Transactional
    public ResponseMessage addMember(TeamMemberRequestDto requestDto) {
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
            if (teamMemberRepository.findByMemberAndTeam(member, team) == null) {
                TeamMember teamMember = TeamMember.builder()
                        .member(member)
                        .team(team)
                        .teamRole(TeamRole.FOLLOWER)
                        .build();
                teamMemberRepository.save(teamMember);
                cnt++;
            }
        }

    return new ResponseMessage("그룹(teamId : " + team.getId() +")에 멤버 " + cnt + "명 추가 완료");
    }

    public List<TeamSimpleResponseDto> getMyTeams() {

        // 회원가입 구현 전까지 member_id = 1인 유저로 하드코딩
        Member me = isPresentMember(1L);
        if (me == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        List<TeamSimpleResponseDto> response = new ArrayList<>();
        List<TeamMember> teamMembers = teamMemberRepository.getAllByMember(me);
        for (TeamMember teamMember : teamMembers) {
            Team team = teamMember.getTeam();

            response.add(TeamSimpleResponseDto.builder()
                    .teamId(team.getId())
                    .name(team.getName())
                    .build());
        }
        return response;
    }

    public ResponseMessage leaveTeam(Long teamId) {

        // 회원가입 구현 전까지 member_id = 1인 유저로 하드코딩
        Member me = isPresentMember(1L);
        if (me == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        Team team = isPresentTeam(teamId);
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }

        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(me, team);
        if (teamMember == null) {
            throw new BadRequestException(TEAM_MEMBER_NOT_FOUND);
        }
        teamMemberRepository.delete(teamMember);

        return new ResponseMessage("그룹(teamId : " + team.getId() +")에서 탈퇴 완료");
    }

    public ResponseMessage memberOut(TeamMemberRequestDto requestDto) {

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

            TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
            if (teamMember == null) {
                throw new BadRequestException(TEAM_MEMBER_NOT_FOUND);
            }

            teamMemberRepository.delete(teamMember);
            cnt++;
        }
        return new ResponseMessage("그룹(teamId : " + team.getId() +")에서 멤버 " + cnt + "명 탈퇴 처리 완료");
    }

    private Team isPresentTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
        return team;
    }

    private Member isPresentMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
        return member;
    }

    private TeamMember isPresentTeamMember(Member member, Team team) {
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
}