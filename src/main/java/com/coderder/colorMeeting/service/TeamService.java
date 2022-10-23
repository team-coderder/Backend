package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.TeamMemberDto;
import com.coderder.colorMeeting.dto.response.TeamMembersResponseDto;
import com.coderder.colorMeeting.dto.response.TeamResponseDto;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamMember;
import com.coderder.colorMeeting.model.TeamRole;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.coderder.colorMeeting.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public TeamResponseDto createTeam(TeamRequestDto requestDto) {
        Team team = Team.builder()
                .name(requestDto.getName())
                .teamMemberList(null)
//                .teamScheduleList(null)
                .build();
        teamRepository.save(team);

        TeamResponseDto response = TeamResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .build();

        return response;
    }

    @Transactional
    public TeamResponseDto updateTeam(Long teamId, TeamRequestDto requestDto) {

        Team team = isPresentTeam(teamId);
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }
        team.updateName(requestDto.getName());

        TeamResponseDto response = TeamResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .build();

        return response;
    }

    @Transactional
    public String deleteTeam(Long teamId) {

        Team team = isPresentTeam(teamId);
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }
        teamRepository.delete(team);

        return "그룹(teamId : " + team.getId() + ") 삭제 완료";
    }

    @Transactional
    public TeamMembersResponseDto getTeamMembers(Long teamId) {
        Team team = isPresentTeam(teamId);
        if (team == null) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }

        // response에 쓰일 멤버 리스트 Dto 생성
        List<TeamMemberDto> members = new ArrayList<>();

        // TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<TeamMember> teamMembers = team.getTeamMemberList();
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

        // response에 쓰일 Dto 생성
        TeamMembersResponseDto response = TeamMembersResponseDto.builder()
                .teamMembers(members)
                .build();

        return response;
    }

    // 그룹에 유저 추가하기 - Member 구현 완료 후 구현 가능
    @Transactional
    public String addMember(TeamMemberRequestDto requestDto) {
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

    return "그룹(teamId : " + team.getId() +")에 멤버 " + cnt + "명 추가 완료";
    }

    public List<TeamResponseDto> getMyTeams() {

        // 회원가입 구현 전까지 member_id = 1인 유저로 하드코딩
        Member me = isPresentMember(1L);
        if (me == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        List<TeamResponseDto> response = new ArrayList<>();
        List<TeamMember> teamMembers = teamMemberRepository.getAllByMember(me);
        for (TeamMember teamMember : teamMembers) {
            Team team = teamMember.getTeam();

            response.add(TeamResponseDto.builder()
                    .teamId(team.getId())
                    .name(team.getName())
                    .build());
        }
        return response;
    }

    public String leaveTeam(Long teamId) {

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
            throw new NotFoundException(TEAM_MEMBER_NOT_FOUND);
        }
        teamMemberRepository.delete(teamMember);

        return "그룹(teamId : " + team.getId() +")에서 탈퇴 완료";
    }

    public String memberOut(TeamMemberRequestDto requestDto) {

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
                throw new NotFoundException(TEAM_MEMBER_NOT_FOUND);
            }

            teamMemberRepository.delete(teamMember);
            cnt++;
        }
        return "그룹(teamId : " + team.getId() +")에서 멤버 " + cnt + "명 탈퇴 처리 완료";
    }

    private Team isPresentTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team.orElse(null);
    }

    private Member isPresentMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElse(null);
    }
}
