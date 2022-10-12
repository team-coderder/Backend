package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
