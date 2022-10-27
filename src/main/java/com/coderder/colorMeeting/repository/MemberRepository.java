package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);

    List<Member> findByNicknameContaining(String keyword);
}
