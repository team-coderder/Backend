package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamResponseDto {
    private Long teamId;
    private String name;
}
