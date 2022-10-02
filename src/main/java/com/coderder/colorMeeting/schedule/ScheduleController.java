package com.coderder.colorMeeting.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/api/schedule/calendar")
    public ScheduleCalendarVO getUserSchedule(@RequestParam(value="userid") String userId){
        ScheduleCalendarVO scheduleCalendarVO = new ScheduleCalendarVO();

        List<ScheduleBlockDto> ScheduleBlockDto = scheduleService.getBlockListById(userId);


        return scheduleCalendarVO;
    }



}
