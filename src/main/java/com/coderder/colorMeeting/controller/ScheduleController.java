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
    public ResponseEntity<List<ScheduleBlockDto>> getUserSchedule(@RequestParam(value="userId") Long userId){
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByUserId(userId);
        return ResponseEntity.ok().body(scheduleBlockDtoList);
    }

    @PatchMapping ("/api/schedule/myschedule")
    public ResponseEntity<ResponseMessage> makeMySchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
        scheduleService.insertScheduleBlock(scheduleRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage("개인 스케쥴 \""+ scheduleRequestDto.getName() + "\" 추가 완료"));
    }

    @GetMapping("/api/schedule/myteam")
    public ResponseEntity<List<ScheduleBlockDto>> getTeamUserAllSchedule(@RequestParam Long teamId)
    {
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByTeamId(teamId);
        return ResponseEntity.ok().body(scheduleBlockDtoList);
    }
    @GetMapping("/api/schedule/recommendations")
    public ResponseEntity<List<RecommendationDto>> getRecommendations(@RequestBody TeamTimeDto teamTimeDto){
        List<RecommendationDto> recommendationList = scheduleService.getTeamEmptyTimes(teamTimeDto);

        return ResponseEntity.ok().body(recommendationList);
    }
    @PostMapping("/api/schedule/teamschedule")
    public ResponseEntity<ResponseMessage> makeTeamSchedule(@RequestBody TeamScheduleRequestDto teamScheduleDto){
        scheduleService.insertGroupSchedule(teamScheduleDto);
        return ResponseEntity.ok().body(new ResponseMessage("팀 스케쥴 \""+teamScheduleDto.getName()+"\" 추가 완료"));
    }
    @GetMapping("/api/schedule/teamschedule")
    public ResponseEntity<List<TeamScheduleDto>> getTeamSchedule(@RequestParam Long teamId){
        List<TeamScheduleDto> teamScheduleDtos = scheduleService.getTeamScheduleList(teamId);
        return ResponseEntity.ok().body(teamScheduleDtos);
    }
}
