package com.coderder.colorMeeting.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@IdClass(TeamMemberId.class)
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private TeamRole teamRole;
}
