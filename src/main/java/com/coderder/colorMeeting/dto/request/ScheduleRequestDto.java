package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private Long userId;
    private String name;
    private String weekday;
    @JsonProperty("start")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonProperty("finish")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finishTime;
    private String memo;
}
