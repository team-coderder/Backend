package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> getAllByMember(Member member);
}
