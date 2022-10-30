package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalTime;

@Builder
@ToString
public class RecommendationDto {
    private String weekday;
    private LocalTime start_time;
    private LocalTime finish_time;
}
