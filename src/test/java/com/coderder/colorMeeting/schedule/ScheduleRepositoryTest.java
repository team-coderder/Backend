package com.coderder.colorMeeting.schedule;

import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.PersonalScheduleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ScheduleRepositoryTest {

    @Autowired
    PersonalScheduleRepository personalScheduleRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void insertScheduleTest(){
        Member member = Member.builder()
                .username("justin")
                .password("1234")
                .nickname("justin")
                .build();
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findById(member.getId()).get().getNickname()).isEqualTo("justin");

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
        Assertions.assertThat(foundSchedule.get().getName()).isEqualTo("test");
    }

}
