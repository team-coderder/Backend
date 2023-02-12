package com.coderder.colorMeeting.dto.response;

import com.coderder.colorMeeting.model.Team;
import lombok.Builder;
import lombok.Getter;

//@Builder
@Getter
public class TeamSimpleResponseDto {
    private Long teamId;
    private String name;

    public TeamSimpleResponseDto(Team team) {
        this.teamId = team.getId();
        this.name = team.getName();
    }
}
