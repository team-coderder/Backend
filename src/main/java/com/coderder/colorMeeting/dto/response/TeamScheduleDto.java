package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Builder
@Getter
@ToString
public class TeamScheduleDto {
    private String name;
    private String weekday;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String memo;
}
