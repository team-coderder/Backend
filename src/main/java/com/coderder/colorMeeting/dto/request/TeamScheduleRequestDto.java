package com.coderder.colorMeeting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@NoArgsConstructor
public class TeamScheduleRequestDto {
    private Long id;
    private Long teamId;
    private String title;
    @JsonProperty("start")
    private String startTime;

    @JsonProperty("end")
    private String finishTime;
    private String memo;
}
