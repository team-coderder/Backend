package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScheduleBlockDto {
    private String userId;
    private String name;
    private String weekday;
    private String startTime;
    private String finishTime;
    private String memo;
    private Long groupId;
}
