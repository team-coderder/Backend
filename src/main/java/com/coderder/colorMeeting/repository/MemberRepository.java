package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m, t " +
            "from Member m join fetch m.teamMemberSet t " +
            "where t.team.id = :teamId")
    List<Member> findAllWithTeamId(@Param("teamId") Long teamId);

    Member findByUsername(String username);

    List<Member> findByNicknameContaining(String keyword);

    List<Member> findByUsernameContaining(String keyword);
}
