package com.coderder.colorMeeting.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class PersonalScheduleListDto extends ScheduleListDto{
    private String username;
}
