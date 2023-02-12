package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private Long id;
    private String title;
    @JsonProperty("start")
    private String startTime;

    @JsonProperty("end")
    private String finishTime;
    private String memo;
}
