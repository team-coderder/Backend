package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.dto.response.GroupResponseDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.GroupNotFoundException;
import com.coderder.colorMeeting.model.Group;
import com.coderder.colorMeeting.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return ResponseDto.success("그룹 저장 완료");
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

        return ResponseDto.success(response);
    }

    private Group isPresent(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.orElse(null);
    }
}
