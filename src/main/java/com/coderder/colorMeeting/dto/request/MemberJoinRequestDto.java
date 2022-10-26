package com.coderder.colorMeeting.dto.request;

import lombok.Data;

@Data
public class MemberJoinRequestDto {
    private String username;
    private String password;
    private String nickname;
}
