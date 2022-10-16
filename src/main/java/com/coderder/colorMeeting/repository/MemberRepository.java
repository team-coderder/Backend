package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
