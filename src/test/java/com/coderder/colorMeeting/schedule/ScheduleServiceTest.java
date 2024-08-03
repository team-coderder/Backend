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
import com.coderder.colorMeeting.repository.PersonalScheduleRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import com.coderder.colorMeeting.repository.TeamScheduleRepository;
import com.coderder.colorMeeting.service.ScheduleService;
import com.coderder.colorMeeting.service.ScheduleServiceImpl;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ScheduleServiceTest {

    ScheduleService scheduleService;
    private PersonalScheduleRepository personalScheduleRepositoryMock;
    private MemberRepository memberRepositoryMock;
    private TeamRepository teamRepositoryMock;
    private TeamScheduleRepository teamScheduleRepositoryMock;

    @BeforeEach
    void init(){
        //todo: 이거 mock 을 일일히 등록시켜주는거 자동화한 annotation 있을법한데.
        personalScheduleRepositoryMock = mock(PersonalScheduleRepository.class);
        memberRepositoryMock = mock(MemberRepository.class);
        teamRepositoryMock = mock(TeamRepository.class);
        teamScheduleRepositoryMock = mock(TeamScheduleRepository.class);

        scheduleService = new ScheduleServiceImpl(personalScheduleRepositoryMock,
                memberRepositoryMock,
                teamRepositoryMock,
                teamScheduleRepositoryMock);
    }


    @Test
    void getBlockListByIdTest(){

    }

    @Test
    void getScheduleBlockTest(){

    }

    @Test
    void teamScheduleTest(){

    }
}
