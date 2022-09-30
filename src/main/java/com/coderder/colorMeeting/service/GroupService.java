package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.ResponseMessage;
import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.model.Group;
import com.coderder.colorMeeting.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public ResponseMessage<?> createGroup(GroupRequestDto requestDto) {
        Group group = Group.builder()
                .name(requestDto.getName())
                .groupMembersList(null)
                .build();
        groupRepository.save(group);
        return ResponseMessage.success("그룹 저장 완료");
    }
}
