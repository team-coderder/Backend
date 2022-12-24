package com.coderder.colorMeeting.schedule;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.TeamScheduleDto;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamSchedule;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import com.coderder.colorMeeting.service.ScheduleService;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;




    @Test
    void getBlockListByIdTest(){
        Member member = insertMember("junggu", "1234", "정구");

        PersonalSchedule personalSchedule =
                insertPersonalSchedule("test", "FRIDAY", LocalTime.of(22, 00),LocalTime.of(22, 30), member);

        List<ScheduleBlockDto> blockDtoList = scheduleService.getBlockListByUserId(personalSchedule.getId());

        assertThat(blockDtoList.get(0).getName())
                .isEqualTo(personalSchedule.getName());
    }

    @Test
    void getBlockWithInvalidUser(){

    }

    @Test
    void insertScheduleBlockTest() {
        Member member = insertMember("junggu", "1234", "정구");

        PersonalSchedule personalSchedule =
                insertPersonalSchedule("test", "FRIDAY", LocalTime.of(22, 00), LocalTime.of(22, 30), member);


        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(
                member.getId(),
                personalSchedule.getName(),
                personalSchedule.getWeekday(),
                personalSchedule.getStartTime().toString(),
                personalSchedule.getFinishTime().toString(),
                personalSchedule.getMemo()
        );

        scheduleService.insertScheduleBlock(scheduleRequestDto);
        List<ScheduleBlockDto> blocks = scheduleService.getBlockListByUserId(member.getId());
        System.out.println(blocks);

        assertThat(blocks.get(0).getName()).isEqualTo(personalSchedule.getName());
    }

    @Test
    void insertScheduleWithInvalidUser(){
        Member member = insertMember("test_junggu", "1234", "정구");

        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(
                member.getId()+1234321,
                "test",
                "FRI",
                "22:00",
                "22:30",
                ""
        );

        ErrorCode errorCode = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            scheduleService.insertScheduleBlock(scheduleRequestDto);
        }).getErrorCode();

        org.junit.jupiter.api.Assertions.assertEquals(errorCode, ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    void insertScheduleWithInvalidTime(){
        Member member = insertMember("test_junggu", "1234", "정구");

        PersonalSchedule personalSchedule =
                insertPersonalSchedule("test", "FRIDAY", LocalTime.of(22, 00), LocalTime.of(22, 30), member);

        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(
                member.getId(),
                personalSchedule.getName(),
                personalSchedule.getWeekday(),
                personalSchedule.getStartTime().toString(),
                personalSchedule.getFinishTime().toString(),
                personalSchedule.getMemo()
        );

        ErrorCode errorCode = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            scheduleService.insertScheduleBlock(scheduleRequestDto);
        }).getErrorCode();

        org.junit.jupiter.api.Assertions.assertEquals(errorCode, ErrorCode.JSON_CONVERSION_ERROR);
    }

    @Test
    void insertScheduleWithInvalidWeekday(){

    }

    @Test
    void insertScheduleWithInvalidName(){

    }

    @Test
    void insertOverlappingSchedule(){

    }

    @Test
    void getTeamAllScheduleWithNonExistingTeam(){

    }

    @Test
    void getTeamScheduleWithNonExistingTeam(){

    }

    @Test
    void getRecommandationWithNonExistingTeam(){

    }

    @Test
    void getRecommandationWithWrongSpendingTime(){
        
    }


    @Test
    void teamScheduleTest(){

        Team team = insertTeam("test_team");

        TeamScheduleRequestDto teamScheduleRequestDto = TeamScheduleRequestDto.builder()
                .name("team_schedule_1")
                .weekday("MON")
                .startTime(LocalTime.of(11, 00))
                .finishTime(LocalTime.of(12, 00))
                .teamId(team.getId())
                .build();

        scheduleService.insertGroupSchedule(teamScheduleRequestDto);

        List<TeamScheduleDto> teamblocks = scheduleService.getTeamScheduleList(team.getId());
        System.out.println(teamblocks);
        assertThat(teamblocks.get(0).getName())
                .isEqualTo(teamScheduleRequestDto.getName());
    }

    private Member insertMember(String username, String password, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        memberRepository.save(member);
        return member;
    }
    private PersonalSchedule insertPersonalSchedule(String name, String weekday, LocalTime startTime, LocalTime finishTime, Member member) {
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name(name)
                .weekday(weekday)
                .startTime(startTime)
                .finishTime(finishTime)
                .member(member)
                .build();
        scheduleService.insertScheduleBlock(personalSchedule);
        return personalSchedule;
    }

    private Team insertTeam(String name) {
        Team team = Team.builder()
                .name(name)
                .build();

        teamRepository.save(team);
        return team;
    }
}
