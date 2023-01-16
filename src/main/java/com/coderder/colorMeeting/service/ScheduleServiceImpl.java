package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.RecommendationDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.TeamScheduleDto;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamSchedule;
import com.coderder.colorMeeting.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private PersonalScheduleRepository personalScheduleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamScheduleRepository teamScheduleRepository;


    public void insertScheduleBlock(PersonalSchedule personalSchedule){
        personalScheduleRepository.save(personalSchedule);
    }

    @Override
    public void insertScheduleBlock(ScheduleRequestDto scheduleRequestDto) {
        Member member = memberRepository.findById(scheduleRequestDto.getUserId())
                .orElseThrow(()->new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name(scheduleRequestDto.getName())
                .weekday(scheduleRequestDto.getWeekday())
                .startTime(scheduleRequestDto.getStartTime())
                .finishTime(scheduleRequestDto.getFinishTime())
                .member(member)
                .build();
        personalScheduleRepository.save(personalSchedule);
    }

    @Override
    public List<ScheduleBlockDto> getBlockListByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<PersonalSchedule> blockList = personalScheduleRepository.findAllByMember(member);
        List<ScheduleBlockDto> blockDtoList = new ArrayList<>();
        for(PersonalSchedule block : blockList){
            ScheduleBlockDto tmpBlock = ScheduleBlockDto.builder()
                    .userId(block.getMember().getUsername())
                    .name(block.getName())
                    .weekday(block.getWeekday())
                    .startTime(block.getStartTime().toString())
                    .finishTime(block.getFinishTime().toString())
                    .memo(block.getMemo())
                    .groupId(block.getGroupScheduleId())
                    .build();
            blockDtoList.add(tmpBlock);
        }
        return blockDtoList;
    }

    @Override
    public List<ScheduleBlockDto> getBlockListByTeamId(Long teamId) {
        List<Member> members = memberRepository.findAllWithTeamId(teamId);
        List<PersonalSchedule> schedules = personalScheduleRepository.findAllByMemberIn(members);

        List<ScheduleBlockDto> blockDtoList
                = new ArrayList<>();
        for(PersonalSchedule schedule : schedules){
            ScheduleBlockDto blockDto = ScheduleBlockDto.builder()
                    .name(schedule.getName())
                    .userId(schedule.getMember().getUsername())
                    .weekday(schedule.getWeekday())
                    .startTime(schedule.getStartTime()
                            .format(DateTimeFormatter.ofPattern("HH:mm")))
                    .finishTime(schedule.getFinishTime()
                            .format(DateTimeFormatter.ofPattern("HH:mm")))
                    .memo(schedule.getMemo())
                    .groupId(schedule.getGroupScheduleId())
                    .build();
            blockDtoList.add(blockDto);
        }

        return blockDtoList;
    }

    @Override
    public List<RecommendationDto> getTeamEmptyTimes(TeamTimeDto teamTimeDto) {
        List<Member> members = memberRepository.findAllWithTeamId(teamTimeDto.getTeamId());
        List<PersonalSchedule> teamSchedules = personalScheduleRepository.findAllByMemberIn(members);

        List<RecommendationDto> recommendationDtos = calculateEmptyTimes(teamSchedules, 2);
        return recommendationDtos;
    }

    private List<RecommendationDto> calculateEmptyTimes(List<PersonalSchedule> personalSchedules, int timeInterval){
        boolean[][] weekCalendar = new boolean[7][24*timeInterval];
        for(PersonalSchedule schedule : personalSchedules){
            int weekday = convertWeekday(schedule.getWeekday());
            int startBlock = convertTime(schedule.getStartTime(), timeInterval);
            int finishBlock = convertTime(schedule.getFinishTime(), timeInterval);
            for(int i=startBlock; i<=finishBlock; i++){
                weekCalendar[weekday][i] = true;
            }
        }

        List<RecommendationDto> times = new ArrayList<>();
        for(int i=0; i<weekCalendar.length; i++){
            for(int j=0; j< weekCalendar[0].length-1; j++){
                if(weekCalendar[i][j]) continue;
                RecommendationDto time = RecommendationDto.builder()
                        .weekday(convertToWeekday(i))
                        .start_time(convertToTime(j, timeInterval))
                        .finish_time(convertToTime(j+1, timeInterval))
                        .build();
                times.add(time);
            }
        }
        return times;
    }
    private int convertWeekday(String weekday){
        if(weekday.equals("mon")) return 0;
        else if(weekday.equals("tue")) return 1;
        else if(weekday.equals("wed")) return 2;
        else if(weekday.equals("thu")) return 3;
        else if(weekday.equals("fri")) return 4;
        else if(weekday.equals("sat")) return 5;
        else if(weekday.equals("sun")) return 6;
        else return -1;
    }
    private int convertTime(LocalTime time, int timeInterval){
        int min = time.getMinute();
        int hour = time.getHour();
        int blockIndex = hour*timeInterval + (min / (60/timeInterval));
        return blockIndex;
    }
    private String convertToWeekday(int weekday){
        if(weekday == 0) return "mon";
        else if(weekday == 1) return "tue";
        else if(weekday == 2) return "wed";
        else if(weekday == 3) return "thu";
        else if(weekday == 4) return "fri";
        else if(weekday == 5) return "sat";
        else if(weekday == 6) return "sun";
        else return "";
    }
    private LocalTime convertToTime(int timeBlock, int timeInterval){
        return LocalTime.of(timeBlock/timeInterval
                , (timeBlock%timeInterval)*(60/timeInterval));
    }


    @Override
    public void insertGroupSchedule(TeamScheduleRequestDto teamScheduleDto) {
        Team team = teamRepository.findById(teamScheduleDto.getTeamId())
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));
        TeamSchedule teamSchedule = TeamSchedule.builder()
                .name(teamScheduleDto.getName())
                .weekday(teamScheduleDto.getWeekday())
                .startTime(teamScheduleDto.getStartTime())
                .finishTime(teamScheduleDto.getFinishTime())
                .team(team)
                .build();
        teamScheduleRepository.save(teamSchedule);
    }

    @Override
    public List<TeamScheduleDto> getTeamScheduleList(Long teamId) {
        List<TeamSchedule> teamSchedules = teamScheduleRepository.findAllByTeamId(teamId);
        List<TeamScheduleDto> teamScheduleDtoList = new ArrayList<>();
        for(TeamSchedule teamSchedule : teamSchedules){
            TeamScheduleDto teamScheduleDto = TeamScheduleDto.builder()
                    .name(teamSchedule.getName())
                    .weekday(teamSchedule.getWeekday())
                    .startTime(teamSchedule.getStartTime())
                    .finishTime(teamSchedule.getFinishTime())
                    .memo(teamSchedule.getMemo())
                    .build();
            teamScheduleDtoList.add(teamScheduleDto);
        }
        return teamScheduleDtoList;
    }

}
