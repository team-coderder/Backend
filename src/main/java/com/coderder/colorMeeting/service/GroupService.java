package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.GroupMemberRequestDto;
import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.dto.response.GroupMemberDto;
import com.coderder.colorMeeting.dto.response.GroupMembersResponseDto;
import com.coderder.colorMeeting.dto.response.GroupResponseDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.GroupNotFoundException;
import com.coderder.colorMeeting.exception.MemberNotFoundException;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamMember;
import com.coderder.colorMeeting.model.TeamRole;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.GroupMemberRepository;
import com.coderder.colorMeeting.repository.GroupRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public ResponseDto<?> createGroup(GroupRequestDto requestDto) {
        Team team = Team.builder()
                .name(requestDto.getName())
                .teamMemberList(null)
//                .groupScheduleList(null)
                .build();
        groupRepository.save(team);
        return ResponseDto.success("그룹(groupId : " + team.getId() +") 저장이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> updateGroup(Long groupId, GroupRequestDto requestDto) {

        Team team = isPresent(groupId);
        if (team == null) {
            throw new GroupNotFoundException();
        }
        team.updateName(requestDto.getName());

        GroupResponseDto response = GroupResponseDto.builder()
                .groupId(team.getId())
                .name(team.getName())
                .build();

        return ResponseDto.success("그룹(groupId : " + team.getId() +") 수정이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> deleteGroup(Long groupId) {

        Team team = isPresent(groupId);
        if (team == null) {
            throw new GroupNotFoundException();
        }
        groupRepository.delete(team);

        return ResponseDto.success("그룹(groupId : " + team.getId() + ") 삭제가 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> getGroupMembers(Long groupId) {
        Team team = isPresent(groupId);
        if (team == null) {
            throw new GroupNotFoundException();
        }

        // response에 쓰일 멤버 리스트 Dto 생성
        List<GroupMemberDto> members = new ArrayList<>();

        // GroupMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<TeamMember> teamMembers = team.getTeamMemberList();
        for (TeamMember teamMember : teamMembers) {
            Member member = teamMember.getMember();
            GroupMemberDto groupMemberDto = GroupMemberDto.builder()
                    .memberId(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .groupRole(String.valueOf(teamMember.getTeamRole()))
                    .build();
            members.add(groupMemberDto);
        }

        // response에 쓰일 Dto 생성
        GroupMembersResponseDto response = GroupMembersResponseDto.builder()
                .groupMembers(members)
                .build();

        return ResponseDto.success(response);
    }

    // 그룹에 유저 추가하기 - Member 구현 완료 후 구현 가능
    @Transactional
    public ResponseDto<?> addMember(GroupMemberRequestDto requestDto) {
        Team team = isPresent(requestDto.getGroupId());
        if (team == null) {
            throw new GroupNotFoundException();
        }

        // Group에 넣기 위해 member 객체 찾아 리스트에 모아두기
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
            // group에 넣을 GroupMember로 변환하기
            TeamMember teamMember = TeamMember.builder()
                    .team(team)
                    .member(member)
                    .teamRole((teamMemberList.size() == 0) ? TeamRole.LEADER : TeamRole.FOLLOWER)
                    .build();
            groupMemberRepository.save(teamMember);
            teamMemberList.add(teamMember);
        }
        int cnt = teamMemberList.size() - before;
    return ResponseDto.success("그룹(groupId : " + team.getId() +")에 멤버 " + cnt + "명 추가가 완료되었습니다.");
    }

    private Team isPresent(Long groupId) {
        Optional<Team> group = groupRepository.findById(groupId);
        return group.orElse(null);
    }
}
