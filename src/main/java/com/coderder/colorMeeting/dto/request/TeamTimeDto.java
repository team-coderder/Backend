package com.coderder.colorMeeting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamTimeDto {
    private Long teamId;
    private int spendingMinute;

}
