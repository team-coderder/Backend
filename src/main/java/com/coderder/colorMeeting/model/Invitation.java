package com.coderder.colorMeeting.model;

import lombok.*;
import org.apache.catalina.Group;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_team_id")
    private Team fromTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_member_id")
    private Member fromLeader;

    @JoinColumn(name="to_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member toMember;

    @Column
    private LocalDateTime createdAt;

}
