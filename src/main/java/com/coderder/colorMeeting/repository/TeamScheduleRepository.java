package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.TeamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamScheduleRepository extends JpaRepository<TeamSchedule, Long> {
    List<TeamSchedule> findAllByTeamId(Long teamId);

    List<TeamSchedule> findAllByTeamIdIn(List<Long> teamIds);
}
