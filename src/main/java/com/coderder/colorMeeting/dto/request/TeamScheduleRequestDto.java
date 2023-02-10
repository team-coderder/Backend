package com.coderder.colorMeeting.dto.request;

import lombok.*;


@Data
@NoArgsConstructor
public class TeamScheduleRequestDto {
    private Long id;
    private Long teamId;
    private String title;
    private String startTime;
    private String finishTime;
    private String memo;
}
