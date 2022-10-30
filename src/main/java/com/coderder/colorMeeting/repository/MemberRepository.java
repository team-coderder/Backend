package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.TeamMember;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m, t " +
            "from Member m join fetch m.teamMemberSet t " +
            "where t.team.name = :teamname")
    List<Member> findAllWithTeamName(@Param("teamname") String teamname);}
    Member findByUsername(String username);

    List<Member> findByNicknameContaining(String keyword);
}
