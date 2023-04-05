package com.coderder.colorMeeting.dto.response;

import com.coderder.colorMeeting.model.Member;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

@SuperBuilder
@Getter
public class AvailableScheduleDto extends ScheduleBlockDto{
    private List<Member> availableMember;
}
