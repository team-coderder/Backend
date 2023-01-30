package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@SuperBuilder
@Getter
@ToString
public class TeamScheduleDto extends ScheduleBlockDto{
    private String name;
    private String memo;
}
