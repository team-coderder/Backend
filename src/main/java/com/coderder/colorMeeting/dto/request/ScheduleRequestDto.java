package com.coderder.colorMeeting.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ScheduleRequestDto {
    private Long userId;
    private String name;
    private String weekday;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String memo;
}
