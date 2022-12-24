package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private Long userId;
    private String name;

    private String weekday;
    private String startTime;

    private String finishTime;
    private String memo;

    public ScheduleRequestDto(Long userId, String name, String weekday, String startTime, String finishTime, String memo) {
        this.userId = userId;
        this.name = name;
        this.weekday = weekday;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.memo = memo;
    }
}
