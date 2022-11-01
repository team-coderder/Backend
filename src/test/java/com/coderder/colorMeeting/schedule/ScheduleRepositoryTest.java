package com.coderder.colorMeeting.schedule;

import com.coderder.colorMeeting.model.*;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.PersonalScheduleRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScheduleRepositoryTest {

    @Autowired
    PersonalScheduleRepository personalScheduleRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void insertScheduleTest(){
        Member member = Member.builder()
                .username("justin")
                .password("1234")
                .nickname("justin")
                .build();
        memberRepository.save(member);

        assertThat(memberRepository.findById(member.getId()).get().getNickname()).isEqualTo("justin");

        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .id(3L)
                .name("test")
                .weekday("MON")
                .member(member)
                .startTime(LocalTime.of(13, 0, 0))
                .finishTime(LocalTime.of(13, 10, 0))
                .groupScheduleId(1L)
                .build();
        System.out.println(personalSchedule);
        personalScheduleRepository.save(personalSchedule);
    }

    @Test
    void findListByIdTest(){
        Member member = Member.builder()
                .username("justin")
                .password("1234")
                .nickname("justin")
                .build();
        memberRepository.save(member);
        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name("test")
                .weekday("MON")
                .member(member)
                .startTime(LocalTime.of(13, 0, 0))
                .finishTime(LocalTime.of(13, 10, 0))
                .groupScheduleId(1L)
                .build();
        personalScheduleRepository.save(personalSchedule);
        Optional<PersonalSchedule> foundSchedule = personalScheduleRepository.findById(personalSchedule.getId());
        assertThat(foundSchedule.get().getName()).isEqualTo("test");
    }
    @Test
    void findByMember_TeamMemberTest(){
        Member member = Member.builder()
                .username("justin")
                .password("1234")
                .nickname("justin")
                .build();
        memberRepository.save(member);
        System.out.println(member);

        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .name("test")
                .weekday("MON")
                .member(member)
                .startTime(LocalTime.of(13, 0, 0))
                .finishTime(LocalTime.of(13, 10, 0))
                .groupScheduleId(1L)
                .build();
        personalScheduleRepository.save(personalSchedule);
        System.out.println(personalSchedule);

        Team team = Team.builder()
                .name("team1")
                .build();
        teamRepository.save(team);


        TeamMember teamMember = TeamMember.builder()
                .member(member)
                .team(team)
                .teamRole(TeamRole.LEADER)
                .build();
        teamMemberRepository.save(teamMember);
        System.out.println(teamMember);

        List<Member> members = memberRepository.findAllWithTeamId(team.getId());
        System.out.println(members);

        List<PersonalSchedule> schedules = personalScheduleRepository.findAllByMemberIn(members);

        assertThat(schedules.get(0).getName())
                .isEqualTo(personalSchedule.getName());
        System.out.println(members.get(0).getPersonalScheduleList());
    }
}
