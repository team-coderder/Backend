package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@Builder
public class RecommendationDto {
    private String weekday;
    private LocalTime start_time;
    private LocalTime finish_time;
}
