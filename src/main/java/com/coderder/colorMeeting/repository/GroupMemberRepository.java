package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<TeamMember, Long> {
}
