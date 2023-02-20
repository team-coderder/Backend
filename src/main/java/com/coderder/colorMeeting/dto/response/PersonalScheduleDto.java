package com.coderder.colorMeeting.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class PersonalScheduleDto extends ScheduleBlockDto {
    private String memberId;
    private String nickname;
    private String title;
    private String memo;
}
