package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.ScheduleCalendarDto;
import com.coderder.colorMeeting.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/api/schedule/calendar")
    public ResponseEntity<ScheduleCalendarDto> getUserSchedule(@RequestParam(value="userid") Long userId){
        List<ScheduleBlockDto> scheduleBlockDto = scheduleService.getBlockListById(userId);
        ScheduleCalendarDto scheduleCalendarDto = ScheduleCalendarDto.builder()
                .blocks(scheduleBlockDto)
                .build();

        return ResponseEntity.ok().body(scheduleCalendarDto);
    }



}
