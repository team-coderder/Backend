package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamTimeDto;
import com.coderder.colorMeeting.dto.response.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void insertScheduleBlock(Member member, ScheduleRequestDto scheduleRequestDto) {
        String[] start = scheduleRequestDto.getStartTime().split("\\+");
        String[] end = scheduleRequestDto.getFinishTime().split("\\+");

        //todo : must handle localtime invalid format exception

        String weekday = start[0];
        LocalTime startTime = LocalTime.parse(start[1]);
        LocalTime endTime = LocalTime.parse(end[1]);
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name(scheduleRequestDto.getTitle())
                .weekday(weekday)
                .startTime(startTime)
                .finishTime(endTime)
                .member(member)
                .build();
        personalScheduleRepository.save(personalSchedule);
    }

    @Override
    public List<PersonalScheduleDto> getBlockListByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<PersonalSchedule> blockList = personalScheduleRepository.findAllByMember(member);
        List<PersonalScheduleDto> blockDtoList = new ArrayList<>();
        for(PersonalSchedule block : blockList){
            PersonalScheduleDto tmpBlock = PersonalScheduleDto.builder()
                    .id(block.getId())
                    .memberId(block.getMember().getUsername())
                    .title(block.getName())
                    .start(block.getWeekday() + "+"
                            + block.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .end(block.getWeekday() + "+"
                            + block.getFinishTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .memo(block.getMemo())
                    .build();
            blockDtoList.add(tmpBlock);
        }
        return blockDtoList;
    }

    @Override
    public List<ScheduleBlockDto> getBlockListByTeamIdWithoutOverlap(Long teamId) {
        List<Member> members = memberRepository.findAllWithTeamId(teamId);
        List<PersonalSchedule> schedules = personalScheduleRepository.findAllByMemberIn(members);

        return getAllGroupMemberSchedule(schedules);
    }

    private List<ScheduleBlockDto> getAllGroupMemberSchedule(List<PersonalSchedule> schedules) {
        boolean[][] minuteCalendar = new boolean[7][24 * 60];

        for(PersonalSchedule schedule : schedules){
            int weekday = convertWeekday(schedule.getWeekday());
            int startBlock = convertTime(schedule.getStartTime(), 60);
            int finishBlock = convertTime(schedule.getFinishTime(), 60);
            for(int i=startBlock; i<=finishBlock; i++){
                minuteCalendar[weekday][i] = true;
            }
        }

        return calendarToScheduleDto(minuteCalendar);
    }

    private List<ScheduleBlockDto> calendarToScheduleDto(boolean[][] calendar) {
        List<ScheduleBlockDto> scheduleBlockDtoList = new ArrayList<>();
        for(int weekday=0; weekday< calendar.length; weekday++){
            boolean isConnectedSchedule = false;
            int start = 0;
            int end = 0;
            for(int minute = 0 ; minute < calendar[0].length; minute++){
                if(!calendar[weekday][minute]){
                    if(isConnectedSchedule) {
                        end = minute-1;
                        ScheduleBlockDto scheduleBlock = ScheduleBlockDto.builder()
                                .start(convertToTime(start, 60).toString())
                                .end(convertToTime(end, 60).toString())
                                .build();
                        scheduleBlockDtoList.add(scheduleBlock);
                    }
                    isConnectedSchedule = false;
                } else if(!isConnectedSchedule) {
                    start = minute;
                    isConnectedSchedule = true;
                }
            }
        }
        return scheduleBlockDtoList;
    }

    @Override
    public List<ScheduleBlockDto> getTeamEmptyTimes(TeamTimeDto teamTimeDto) {
        List<Member> members = memberRepository.findAllWithTeamId(teamTimeDto.getTeamId());
        List<PersonalSchedule> teamSchedules = personalScheduleRepository.findAllByMemberIn(members);

        List<ScheduleBlockDto> recommendationDtos = calculateEmptyTimes(teamSchedules, 2);
        return recommendationDtos;
    }

    private List<ScheduleBlockDto> calculateEmptyTimes(List<PersonalSchedule> personalSchedules, int timeInterval){
        boolean[][] weekCalendar = new boolean[7][24*timeInterval];
        for(PersonalSchedule schedule : personalSchedules){
            int weekday = convertWeekday(schedule.getWeekday());
            int startBlock = convertTime(schedule.getStartTime(), timeInterval);
            int finishBlock = convertTime(schedule.getFinishTime(), timeInterval);
            for(int i=startBlock; i<=finishBlock; i++){
                weekCalendar[weekday][i] = true;
            }
        }

        List<ScheduleBlockDto> times = new ArrayList<>();
        for(int i=0; i<weekCalendar.length; i++){
            for(int j=0; j< weekCalendar[0].length-1; j++){
                if(weekCalendar[i][j]) continue;
                ScheduleBlockDto time = ScheduleBlockDto.builder()
                        .start(convertToTime(j, timeInterval).toString())
                        .end(convertToTime(j+1, timeInterval).toString())
                        .build();
                times.add(time);
            }
        }
        return times;
    }
    private int convertWeekday(String weekday){
        if(weekday.equalsIgnoreCase("mon") || weekday.equalsIgnoreCase("monday")) return 0;
        else if(weekday.equalsIgnoreCase("tue") || weekday.equalsIgnoreCase("tuesday")) return 1;
        else if(weekday.equalsIgnoreCase("wed") || weekday.equalsIgnoreCase("wednesday")) return 2;
        else if(weekday.equalsIgnoreCase("thu") || weekday.equalsIgnoreCase("thursday")) return 3;
        else if(weekday.equalsIgnoreCase("fri") || weekday.equalsIgnoreCase("friday")) return 4;
        else if(weekday.equalsIgnoreCase("sat") || weekday.equalsIgnoreCase("saturday")) return 5;
        else if(weekday.equalsIgnoreCase("sun") || weekday.equalsIgnoreCase("sunday")) return 6;
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

        String[] start = teamScheduleDto.getStartTime().split("\\+");
        String[] end = teamScheduleDto.getFinishTime().split("\\+");

        //todo : must handle localtime invalid format exception

        String weekday = start[0];
        LocalTime startTime = LocalTime.parse(start[1]);
        LocalTime endTime = LocalTime.parse(end[1]);
        TeamSchedule teamSchedule = TeamSchedule.builder()
                .id(teamScheduleDto.getId())
                .name(teamScheduleDto.getTitle())
                .weekday(weekday)
                .startTime(startTime)
                .finishTime(endTime)
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
                    .id(teamSchedule.getId())
                    .title(teamSchedule.getName())
                    .start(teamSchedule.getWeekday() +"+"
                            +teamSchedule.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .end(teamSchedule.getWeekday() +"+"
                            + teamSchedule.getFinishTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .memo(teamSchedule.getMemo())
                    .build();
            teamScheduleDtoList.add(teamScheduleDto);
        }
        return teamScheduleDtoList;
    }

    @Override
    public List<PersonalScheduleListDto> getBlockListByTeamId(Long teamId) {
        List<PersonalScheduleListDto> personalScheduleList = new ArrayList<>();
        List<Member> members = memberRepository.findAllWithTeamId(teamId);
        List<PersonalSchedule> schedules = personalScheduleRepository.findAllByMemberIn(members);

        Map<String, List<PersonalScheduleDto>> teammateScheduleMap = new HashMap<>();
        for(PersonalSchedule schedule : schedules){
            List<PersonalScheduleDto> scheduleListDto;
            if(!teammateScheduleMap.containsKey(schedule.getMember().getUsername())){
                scheduleListDto = new ArrayList<>();
                teammateScheduleMap.put(schedule.getMember().getUsername(), scheduleListDto);
            } else {
                scheduleListDto = teammateScheduleMap.get(schedule.getMember().getUsername());
            }
            scheduleListDto.add(PersonalScheduleDto.builder()
                            .id(schedule.getId())
                            .title(schedule.getName())
                            .start(schedule.getWeekday()+"+"
                                    + schedule.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                            .end(schedule.getWeekday()+"+"
                                    + schedule.getFinishTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                            .memberId(schedule.getMember().getUsername())
                            .memo(schedule.getMemo())
                    .build());
        }

        for(String username : teammateScheduleMap.keySet()) {
            PersonalScheduleListDto personalScheduleListDto = PersonalScheduleListDto.builder()
                    .username(username)
                    .schedule(teammateScheduleMap.get(username))
                    .build();
            personalScheduleList.add(personalScheduleListDto);
        }
        return personalScheduleList;
    }

    @Override
    public void updateScheduleBlock(Member member, ScheduleRequestDto scheduleRequestDto) {
        if(scheduleRequestDto.getId() == null){
            //todo : throw exception when scheduleBlock Id is null
        }
        String[] start = scheduleRequestDto.getStartTime().split("\\+");
        String[] end = scheduleRequestDto.getFinishTime().split("\\+");

        //todo : must handle localtime invalid format exception

        String weekday = start[0];
        LocalTime startTime = LocalTime.parse(start[1]);
        LocalTime endTime = LocalTime.parse(end[1]);
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .id(scheduleRequestDto.getId())
                .name(scheduleRequestDto.getTitle())
                .weekday(weekday)
                .startTime(startTime)
                .finishTime(endTime)
                .member(member)
                .build();
        personalScheduleRepository.save(personalSchedule);
    }

    @Override
    public void deleteScheduleBlock(Member member, Long scheduleId) {
        personalScheduleRepository.deleteById(scheduleId);
    }

    @Override
    public void updateGroupSchedule(TeamScheduleRequestDto teamScheduleDto) {
        if(teamScheduleDto.getId() == null){
            //todo : throw exception when scheduleBlock Id is null
        }
        String[] start = teamScheduleDto.getStartTime().split("\\+");
        String[] end = teamScheduleDto.getFinishTime().split("\\+");

        //todo : must handle localtime invalid format exception

        String weekday = start[0];
        LocalTime startTime = LocalTime.parse(start[1]);
        LocalTime endTime = LocalTime.parse(end[1]);
        TeamSchedule teamSchedule = TeamSchedule.builder()
                .id(teamScheduleDto.getId())
                .name(teamScheduleDto.getTitle())
                .weekday(weekday)
                .startTime(startTime)
                .finishTime(endTime)
                .build();
        teamScheduleRepository.save(teamSchedule);
    }

    @Override
    public void deleteGroupSchedule(Long scheduleId) {
        teamScheduleRepository.deleteById(scheduleId);
    }
}
