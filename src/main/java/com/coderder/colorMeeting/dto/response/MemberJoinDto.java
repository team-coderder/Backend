package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberJoinDto {
    private String username;
    private String password;
    private String nickname;
    private String roles;
}
