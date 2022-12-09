package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;


@Data
@Builder
public class TeamScheduleRequestDto {
    private Long teamId;
    private String name;
    private String weekday;
    @JsonProperty("start-time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonProperty("finish-time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finishTime;
    private String memo;
}
