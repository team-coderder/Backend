package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Invitation;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Invitation findByToMemberAndFromTeam(Member toMember, Team fromTeam);
    List<Invitation> findAllByToMember(Member toMember);
}
