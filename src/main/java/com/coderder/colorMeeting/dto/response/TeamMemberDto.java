package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamMemberDto {
    private Long id;
    private String username;
    private String nickname;
    private String teamRole;
}
