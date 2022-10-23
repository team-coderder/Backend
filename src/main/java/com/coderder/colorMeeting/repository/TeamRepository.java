package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
