package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Team, Long> {
}
