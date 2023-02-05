package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/api/schedule/calendar")
    public ResponseEntity<ScheduleListDto> getUserSchedule(@RequestParam(value="memberId") Long userId){
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .blocks(scheduleService.getBlockListByUserId(userId))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }

    @GetMapping("/api/schedule/myschedule")
    public ResponseEntity<ScheduleListDto> getMySchedule(@AuthenticationPrincipal PrincipalDetails userDetails){
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .blocks(scheduleService.getBlockListByUserId(userDetails.getMember().getId()))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }

    @PatchMapping ("/api/schedule/myschedule")
    public ResponseEntity<ResponseMessage> makeMySchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
        scheduleService.insertScheduleBlock(scheduleRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage("개인 스케쥴 "+ scheduleRequestDto.getName() + "추가 완료"));
    }

    @GetMapping("/api/schedule/myteam")
    public ResponseEntity<ScheduleListDto> getTeamUserAllSchedule(@RequestParam Long teamId)
    {
        List<ScheduleBlockDto> scheduleBlockDtoList = scheduleService.getBlockListByTeamId(teamId);
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .blocks(scheduleBlockDtoList).build();
        return ResponseEntity.ok().body(scheduleListDto);
    }
    @GetMapping("/api/schedule/recommendations")
    public ResponseEntity<ScheduleListDto> getRecommendations(@RequestBody TeamTimeDto teamTimeDto){
        List<ScheduleBlockDto> recommendationList = scheduleService.getTeamEmptyTimes(teamTimeDto);
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .blocks(recommendationList).build();
        return ResponseEntity.ok().body(scheduleListDto);
    }
    @PostMapping("/api/schedule/teamschedule")
    public ResponseEntity<ResponseMessage> makeTeamSchedule(@RequestBody TeamScheduleRequestDto teamScheduleDto){
        scheduleService.insertGroupSchedule(teamScheduleDto);
        return ResponseEntity.ok().body(new ResponseMessage("팀 스케쥴 "+teamScheduleDto.getName()+" 추가 완료"));
    }
    @GetMapping("/api/schedule/teamschedule")
    public ResponseEntity<ScheduleListDto> getTeamSchedule(@RequestParam Long teamId){
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .blocks(scheduleService.getTeamScheduleList(teamId))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }
}
