package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private Long memberId;
    private String name;
    private String weekday;
    @JsonProperty("startTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonProperty("finishTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finishTime;
    private String memo;
}
