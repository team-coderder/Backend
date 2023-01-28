package com.coderder.colorMeeting.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@IdClass(TeamMemberId.class)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamMember {

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="team_id")
    private Team team;

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private TeamRole teamRole;
}
