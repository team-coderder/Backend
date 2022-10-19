package com.coderder.colorMeeting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<PersonalSchedule> personalScheduleList;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMemberSet;
}
