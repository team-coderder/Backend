package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.model.Member;

import java.util.List;

public interface ScheduleService {

    void insertScheduleBlock(Member member, ScheduleRequestDto scheduleRequestDto);

    List<PersonalScheduleDto> getBlockListByUserId(Member member);

    List<ScheduleBlockDto> getBlockListByTeamIdWithoutOverlap(Long teamId);


    void insertGroupSchedule(TeamScheduleRequestDto teamScheduleDto);

    List<TeamScheduleDto> getTeamScheduleList(Long teamId);


    List<PersonalScheduleListDto> getBlockListByTeamId(Long teamId);

    void updateScheduleBlock(Member member, ScheduleRequestDto scheduleRequestDto);

    void deleteScheduleBlock(Member member, Long scheduleId);

    void updateGroupSchedule(TeamScheduleRequestDto teamScheduleDto);

    void deleteGroupSchedule(Long scheduleId);

    RandomRecommendationDto getRandomRecommandation(Long teamId, Integer spanTime);
}
