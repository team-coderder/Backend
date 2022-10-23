package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.model.PersonalSchedule;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScheduleService {
    public void insertScheduleBlock(PersonalSchedule personalSchedule);
    List<ScheduleBlockDto> getBlockListById(Long userId);
}
