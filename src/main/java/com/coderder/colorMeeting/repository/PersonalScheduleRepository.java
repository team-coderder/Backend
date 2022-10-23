package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
    List<PersonalSchedule> findAllById(Long id);
}
