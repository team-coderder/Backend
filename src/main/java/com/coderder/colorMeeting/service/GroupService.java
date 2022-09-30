package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.dto.response.GroupMemberDto;
import com.coderder.colorMeeting.dto.response.GroupMembersResponseDto;
import com.coderder.colorMeeting.dto.response.GroupResponseDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.GroupNotFoundException;
import com.coderder.colorMeeting.model.Group;
import com.coderder.colorMeeting.model.GroupMembers;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.GroupRepository;
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

    @Transactional
    public ResponseDto<?> createGroup(GroupRequestDto requestDto) {
        Group group = Group.builder()
                .name(requestDto.getName())
                .groupMembersList(null)
//                .groupScheduleList(null)
                .build();
        groupRepository.save(group);
        return ResponseDto.success("그룹 저장이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> updateGroup(Long groupId, GroupRequestDto requestDto) {

        Group group = isPresent(groupId);
        if (group == null) {
            throw new GroupNotFoundException();
        }
        group.updateName(requestDto.getName());

        GroupResponseDto response = GroupResponseDto.builder()
                .groupId(group.getId())
                .name(group.getName())
                .build();

//        return ResponseDto.success(response);
        return ResponseDto.success("그룹 수정이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> deleteGroup(Long groupId) {

        Group group = isPresent(groupId);
        if (group == null) {
            throw new GroupNotFoundException();
        }
        groupRepository.delete(group);

        return ResponseDto.success("그룹 삭제가 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<?> getGroupMembers(Long groupId) {
        Group group = isPresent(groupId);
        if (group == null) {
            throw new GroupNotFoundException();
        }

        // response에 쓰일 멤버 리스트 Dto 생성
        List<GroupMemberDto> members = new ArrayList<>();

        // GroupMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<GroupMembers> groupMembers = group.getGroupMembersList();
        for (GroupMembers groupMember : groupMembers) {
            Member member = groupMember.getMember();
            GroupMemberDto groupMemberDto = GroupMemberDto.builder()
                    .memberId(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .groupRole(String.valueOf(groupMember.getGroupRole()))
                    .build();
            members.add(groupMemberDto);
        }

        // response에 쓰일 Dto 생성
        GroupMembersResponseDto response = GroupMembersResponseDto.builder()
                .groupMembers(members)
                .build();

        return ResponseDto.success(response);
    }

    private Group isPresent(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.orElse(null);
    }
}
