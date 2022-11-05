package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    List<Member> findByNicknameContaining(String keyword);
}
