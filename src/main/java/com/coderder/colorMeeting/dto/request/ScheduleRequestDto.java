package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private Long userId;
    private String name;

    @JsonProperty("weekday")
    private DayOfWeek weekday;
    @JsonProperty("start-time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonProperty("finish-time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finishTime;
    private String memo;
}
