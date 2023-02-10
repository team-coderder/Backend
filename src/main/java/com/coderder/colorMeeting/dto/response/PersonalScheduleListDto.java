package com.coderder.colorMeeting.dto.response;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class PersonalScheduleListDto extends ScheduleListDto{
    private String username;
}
