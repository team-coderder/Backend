package com.coderder.colorMeeting.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@SuperBuilder
@Getter
public class ScheduleBlockDto {
    private String weekday;
    private LocalTime startTime;
    private LocalTime finishTime;
}
