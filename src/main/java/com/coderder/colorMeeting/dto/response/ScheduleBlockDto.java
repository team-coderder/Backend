package com.coderder.colorMeeting.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ScheduleBlockDto {
    private Long id;
    private String start;
    private String end;
}
