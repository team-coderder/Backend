package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ScheduleListDto {
    List<? extends ScheduleBlockDto> blocks;
}