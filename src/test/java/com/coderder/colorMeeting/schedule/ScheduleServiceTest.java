package com.coderder.colorMeeting.schedule;

import com.coderder.colorMeeting.dto.request.ScheduleRequestDto;
import com.coderder.colorMeeting.dto.request.TeamScheduleRequestDto;
import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.dto.response.TeamScheduleDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamSchedule;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import com.coderder.colorMeeting.service.ScheduleService;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = Member.builder()
                .username("junggu")
                .password("1234")
                .nickname("정구")
                .build();

        memberRepository.save(member);

        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name("test")
                .weekday("FRI")
                .startTime(LocalTime.of(22,00))
                .finishTime(LocalTime.of(23,30))
                .member(member)
                .build();
        scheduleService.insertScheduleBlock(personalSchedule);
        List<ScheduleBlockDto> blockDtoList = scheduleService.getBlockListByUserId(personalSchedule.getId());

        assertThat(blockDtoList.get(0).getName())
                .isEqualTo(personalSchedule.getName());

    }

    @Test
    void getScheduleBlockTest(){
        Member member = Member.builder()
                .username("junggu")
                .password("1234")
                .nickname("정구")
                .build();

        memberRepository.save(member);

        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name("test")
                .weekday("FRI")
                .startTime(LocalTime.of(22,00))
                .finishTime(LocalTime.of(23,30))
                .member(member)
                .build();

        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder()
                .userId(member.getId())
                .weekday(personalSchedule.getWeekday())
                .name(personalSchedule.getName())
                .startTime(personalSchedule.getStartTime())
                .finishTime(personalSchedule.getFinishTime())
                .memo(personalSchedule.getMemo())
                .build();

        scheduleService.insertScheduleBlock(scheduleRequestDto);
        List<ScheduleBlockDto> blocks = scheduleService.getBlockListByUserId(member.getId());
        System.out.println(blocks);

        assertThat(blocks.get(0).getName()).isEqualTo(personalSchedule.getName());
    }

    @Test
    void teamScheduleTest(){
        Team team = Team.builder()
                .name("test_team")
                .build();

        teamRepository.save(team);

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
}
