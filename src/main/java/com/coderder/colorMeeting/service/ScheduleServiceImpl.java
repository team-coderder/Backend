package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.response.ScheduleBlockDto;
import com.coderder.colorMeeting.model.PersonalSchedule;
import com.coderder.colorMeeting.repository.PersonalScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private PersonalScheduleRepository personalScheduleRepository;

    public void insertScheduleBlock(PersonalSchedule personalSchedule){
        personalScheduleRepository.save(personalSchedule);
    }

    @Override
    public List<ScheduleBlockDto> getBlockListById(Long userId) {
        List<PersonalSchedule> blockList = personalScheduleRepository.findAllById(userId);
        List<ScheduleBlockDto> blockDtoList = new ArrayList<>();
        for(PersonalSchedule block : blockList){
            ScheduleBlockDto tmpBlock = ScheduleBlockDto.builder()
                    .userid(block.getId())
                    .name(block.getName())
                    .weekday(block.getWeekday())
                    .start_time(block.getStartTime().toString())
                    .end_time(block.getFinishTime().toString())
                    .memo(block.getMemo())
                    .group_id(block.getGroupScheduleId())
                    .build();
            blockDtoList.add(tmpBlock);
        }
        return blockDtoList;
    }
}
