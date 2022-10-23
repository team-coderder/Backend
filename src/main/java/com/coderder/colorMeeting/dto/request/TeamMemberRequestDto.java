package com.coderder.colorMeeting.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class TeamMemberRequestDto {
    private Long teamId;
    private List<Long> memberIds;
}
