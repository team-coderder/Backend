package com.coderder.colorMeeting.model;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "team")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<TeamSchedule> teamScheduleList;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<TeamMember> teamMemberList;

    public void updateName(String name) {
        this.name = name;
    }

//    public void updateGroupMemberList(List<GroupMember> groupMembers) {
//        this.groupMemberList = groupMembers;
//    }
}
