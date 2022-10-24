package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.ScheduleCalendarDto;
import com.coderder.colorMeeting.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/api/schedule/calendar")
    public ResponseEntity<ScheduleCalendarDto> getUserSchedule(@RequestParam(value="userid") Long userId){
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListById(userId);
        ScheduleCalendarDto scheduleCalendarDto = ScheduleCalendarDto.builder()
                .blocks(scheduleBlockDtoList)
                .build();

        return ResponseEntity.ok().body(scheduleCalendarDto);
    }

    @PatchMapping ("/api/schedule/myschedule")
    public ResponseEntity<Long> makeMySchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
        scheduleService.insertScheduleBlock(scheduleRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/schedule/mygroup")
    public ResponseEntity<ScheduleCalendarDto> getGroupUserAllSchedule(@RequestParam String groupId)
    {
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByGroupId(groupId);
        ScheduleCalendarDto scheduleCalendarDto = ScheduleCalendarDto.builder()
                .blocks(scheduleBlockDtoList)
                .build();

        return ResponseEntity.ok().body(scheduleCalendarDto);
    }

}
