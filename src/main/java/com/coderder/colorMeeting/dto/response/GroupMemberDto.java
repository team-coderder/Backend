package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupMemberDto {
    private Long memberId;
    private String username;
    private String nickname;
    private String groupRole;
}
