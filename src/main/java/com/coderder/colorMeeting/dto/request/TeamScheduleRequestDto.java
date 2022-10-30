package com.coderder.colorMeeting.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Builder
@ToString
public class TeamScheduleRequestDto {
    private Long teamId;
    private String name;
    private String weekday;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String memo;
}
