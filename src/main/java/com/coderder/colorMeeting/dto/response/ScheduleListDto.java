package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class ScheduleListDto {
    List<? extends ScheduleBlockDto> schedule;
}
