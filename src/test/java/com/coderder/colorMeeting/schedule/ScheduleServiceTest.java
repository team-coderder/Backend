package com.coderder.colorMeeting.schedule;

import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.service.ScheduleService;
import org.assertj.core.api.Assertions;
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
        List<ScheduleBlockDto> blockDtoList = scheduleService.getBlockListById(personalSchedule.getId());

        assertThat(blockDtoList.get(0).getName())
                .isEqualTo(personalSchedule.getName());

    }
}
