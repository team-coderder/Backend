package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Builder
@ToString
public class TeamScheduleResponseDto {
    private List<TeamScheduleDto> teamScheduleDtoList;
}
