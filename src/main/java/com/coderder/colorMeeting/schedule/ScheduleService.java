package com.coderder.colorMeeting.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    List<ScheduleBlockDto> getBlockListById(String userId);
}
