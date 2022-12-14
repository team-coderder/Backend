package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.RecommendationDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.TeamScheduleDto;
import com.coderder.colorMeeting.model.PersonalSchedule;

import java.util.List;

public interface ScheduleService {


    void insertScheduleBlock(PersonalSchedule personalSchedule);

    void insertScheduleBlock(ScheduleRequestDto scheduleRequestDtoDto);
    List<ScheduleBlockDto> getBlockListByUserId(Long userId);

    List<ScheduleBlockDto> getBlockListByTeamId(Long teamId);

    List<RecommendationDto> getTeamEmptyTimes(TeamTimeDto teamTimeDto);

    void insertGroupSchedule(TeamScheduleRequestDto teamScheduleDto);

    List<TeamScheduleDto> getTeamScheduleList(Long teamId);
}
