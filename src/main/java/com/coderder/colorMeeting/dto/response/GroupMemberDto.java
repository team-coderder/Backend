package com.coderder.colorMeeting.dto.response;

import lombok.Builder;

@Builder
public class GroupMemberDto {
    private Long memberId;
    private String username;
    private String nickname;
    private String groupRole;
}
