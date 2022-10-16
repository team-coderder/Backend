package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.TeamMemberDto;
import com.coderder.colorMeeting.dto.response.TeamMembersResponseDto;
import com.coderder.colorMeeting.dto.response.TeamResponseDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.TeamNotFoundException;
import com.coderder.colorMeeting.exception.MemberNotFoundException;
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

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public ResponseDto<?> createTeam(TeamRequestDto requestDto) {
        Team team = Team.builder()
                .name(requestDto.getName())
                .teamMemberList(null)
//                .teamScheduleList(null)
                .build();
        teamRepository.save(team);
        return ResponseDto.success("그룹(teamId : " + team.getId() +") 저장이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> updateTeam(Long teamId, TeamRequestDto requestDto) {

        Team team = isPresent(teamId);
        if (team == null) {
            throw new TeamNotFoundException();
        }
        team.updateName(requestDto.getName());

        TeamResponseDto response = TeamResponseDto.builder()
                .teamId(team.getId())
                .name(team.getName())
                .build();

        return ResponseDto.success("그룹(teamId : " + team.getId() +") 수정이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> deleteTeam(Long teamId) {

        Team team = isPresent(teamId);
        if (team == null) {
            throw new TeamNotFoundException();
        }
        teamRepository.delete(team);

        return ResponseDto.success("그룹(teamId : " + team.getId() + ") 삭제가 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> getTeamMembers(Long teamId) {
        Team team = isPresent(teamId);
        if (team == null) {
            throw new TeamNotFoundException();
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

        return ResponseDto.success(response);
    }

    // 그룹에 유저 추가하기 - Member 구현 완료 후 구현 가능
    @Transactional
    public ResponseDto<?> addMember(TeamMemberRequestDto requestDto) {
        Team team = isPresent(requestDto.getTeamId());
        if (team == null) {
            throw new TeamNotFoundException();
        }

        // Team에 넣기 위해 member 객체 찾아 리스트에 모아두기
        List<Long> memberIds = requestDto.getMemberIds();
        List<Member> members = new ArrayList<>();
        for (Long memberId : memberIds) {
            Member member = memberRepository.find(memberId);
            if (member == null) {
                throw new MemberNotFoundException();
            }
            members.add(member);
        }

        List<TeamMember> teamMemberList = team.getTeamMemberList();
        int before = teamMemberList.size();
        for (Member member : members) {
            // team에 넣을 TeamMember로 변환하기
            TeamMember teamMember = TeamMember.builder()
                    .team(team)
                    .member(member)
                    .teamRole((teamMemberList.size() == 0) ? TeamRole.LEADER : TeamRole.FOLLOWER)
                    .build();
            teamMemberRepository.save(teamMember);
            teamMemberList.add(teamMember);
        }
        int cnt = teamMemberList.size() - before;
    return ResponseDto.success("그룹(teamId : " + team.getId() +")에 멤버 " + cnt + "명 추가가 완료되었습니다.");
    }

    private Team isPresent(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team.orElse(null);
    }
}
