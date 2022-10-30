package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.*;
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
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByUserId(userId);
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

    @GetMapping("/api/schedule/myteam")
    public ResponseEntity<ScheduleCalendarDto> getGroupUserAllSchedule(@RequestParam String teamId)
    {
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByGroupId(teamId);
        ScheduleCalendarDto scheduleCalendarDto = ScheduleCalendarDto.builder()
                .blocks(scheduleBlockDtoList)
                .build();

        return ResponseEntity.ok().body(scheduleCalendarDto);
    }
    @GetMapping("/api/schedule/recommendations")
    public ResponseEntity<RecommendationListDto> getRecommendations(@RequestBody TeamTimeDto teamTimeDto){
        List<RecommendationDto> recommendationList = scheduleService.getTeamEmptyTimes(teamTimeDto.getTeamId(), teamTimeDto.getSpendingMinute());
        RecommendationListDto recommendationListDto = RecommendationListDto.builder()
                .recommendationListDto(recommendationList)
                .build();

        return ResponseEntity.ok().body(recommendationListDto);
    }
    @PostMapping("/api/schedule/teamschedule")
    public ResponseEntity<Long> makeTeamSchedule(@RequestBody TeamScheduleRequestDto teamScheduleDto){
        scheduleService.insertGroupSchedule(teamScheduleDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/api/schedule/teamschedule")
    public ResponseEntity<TeamScheduleResponseDto> getTeamSchedule(@RequestParam Long teamId){
        List<TeamScheduleDto> teamScheduleDtos = scheduleService.getTeamScheduleList(teamId);
        TeamScheduleResponseDto teamScheduleResponseDto = TeamScheduleResponseDto.builder()
                .teamScheduleDtoList(teamScheduleDtos)
                .build();
        return ResponseEntity.ok().body(teamScheduleResponseDto);
    }
}
