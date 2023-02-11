package com.coderder.colorMeeting.dto.response;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class TeamScheduleDto extends ScheduleBlockDto{
    private String title;
    private String memo;
}
