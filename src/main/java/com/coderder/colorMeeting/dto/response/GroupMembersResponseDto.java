package com.coderder.colorMeeting.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public class GroupMembersResponseDto {
    private List<GroupMemberDto> groupMembers;
}
