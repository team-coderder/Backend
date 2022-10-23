package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScheduleBlockDto {
    private Long userid;
    private String name;
    private String weekday;
    private String start_time;
    private String end_time;
    private String memo;
    private Long group_id;
}
