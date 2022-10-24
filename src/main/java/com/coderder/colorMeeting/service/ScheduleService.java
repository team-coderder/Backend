package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.model.PersonalSchedule;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScheduleService {


    void insertScheduleBlock(PersonalSchedule personalSchedule);

    void insertScheduleBlock(ScheduleRequestDto scheduleRequestDtoDto);
    List<ScheduleBlockDto> getBlockListById(Long userId);

    List<ScheduleBlockDto> getBlockListByGroupId(String groupId);
}
