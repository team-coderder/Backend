package com.coderder.colorMeeting.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class GroupMemberRequestDto {
    private Long groupId;
    private List<Long> memberIds;
}
