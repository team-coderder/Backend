package com.coderder.colorMeeting.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public class ScheduleCalendarDto {
    private List<ScheduleBlockDto> blocks;

    public ScheduleCalendarDto(List<ScheduleBlockDto> blocks) {
        this.blocks = blocks;
    }
}
