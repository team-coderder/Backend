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
                .schedule(scheduleService.getBlockListByUserId(userId))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }

    @GetMapping("/api/schedule/myschedule")
    public ResponseEntity<ScheduleListDto> getMySchedule(@AuthenticationPrincipal PrincipalDetails userDetails){
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .schedule(scheduleService.getBlockListByUserId(userDetails.getMember().getId()))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }

    @PostMapping("/api/schedule/myschedule")
    public ResponseEntity<ResponseMessage> makeMySchedule(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                          @RequestBody ScheduleRequestDto scheduleRequestDto){
        scheduleService.insertScheduleBlock(userDetails.getMember(), scheduleRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage("개인 스케쥴 "+ scheduleRequestDto.getTitle() + "추가 완료"));
    }

    @PatchMapping("/api/schedule/myschedule")
    public ResponseEntity<ResponseMessage> changeMySchedule(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                          @RequestBody ScheduleRequestDto scheduleRequestDto){
        scheduleService.updateScheduleBlock(userDetails.getMember(), scheduleRequestDto);
        return ResponseEntity.ok().body(new ResponseMessage(""));
    }

    @DeleteMapping("/api/schedule/myschedule")
    public ResponseEntity<ResponseMessage> deleteMySchedule(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                            @RequestParam Long scheduleId){
        scheduleService.deleteScheduleBlock(userDetails.getMember(), scheduleId);
        return ResponseEntity.ok().body(new ResponseMessage("schedule id : "+scheduleId+" delete complete"));
    }

    @GetMapping("/api/schedule/myteam")
    public ResponseEntity<List<PersonalScheduleListDto>> getTeamUserAllSchedule(@RequestParam Long teamId)
    {
        List<PersonalScheduleListDto> scheduleBlockDtoList = scheduleService.getBlockListByTeamId(teamId);

        return ResponseEntity.ok().body(scheduleBlockDtoList);
    }
    @GetMapping("/api/schedule/recommendations")
    public ResponseEntity<ScheduleListDto> getRecommendations(@RequestBody TeamTimeDto teamTimeDto){
        List<ScheduleBlockDto> recommendationList = scheduleService.getTeamEmptyTimes(teamTimeDto);
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .schedule(recommendationList).build();
        return ResponseEntity.ok().body(scheduleListDto);
    }
    @PostMapping("/api/schedule/teamschedule")
    public ResponseEntity<ResponseMessage> makeTeamSchedule(@RequestBody TeamScheduleRequestDto teamScheduleDto){
        scheduleService.insertGroupSchedule(teamScheduleDto);
        return ResponseEntity.ok().body(new ResponseMessage("팀 스케쥴 "+teamScheduleDto.getTitle()+" 추가 완료"));
    }
    @GetMapping("/api/schedule/teamschedule")
    public ResponseEntity<ScheduleListDto> getTeamSchedule(@RequestParam Long teamId){
        ScheduleListDto scheduleListDto = ScheduleListDto.builder()
                .schedule(scheduleService.getTeamScheduleList(teamId))
                .build();
        return ResponseEntity.ok().body(scheduleListDto);
    }

    @PatchMapping("/api/schedule/teamschedule")
    public ResponseEntity<ResponseMessage> changeTeamSchedule(@RequestParam TeamScheduleRequestDto teamScheduleDto){
        scheduleService.updateGroupSchedule(teamScheduleDto);
        return ResponseEntity.ok().body(new ResponseMessage("팀 스케쥴 "+teamScheduleDto.getTitle()+" 추가 완료"));
    }

    @DeleteMapping("/api/schedule/teamschedule")
    public ResponseEntity<ResponseMessage> deleteTeamSchedule(@RequestParam Long scheduleId) {
        scheduleService.deleteGroupSchedule(scheduleId);
        return ResponseEntity.ok().body(new ResponseMessage("teamSchedule id : "+scheduleId+" delete complete"));
    }
}
