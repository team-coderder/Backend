package com.coderder.colorMeeting.model;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity(name = "groups")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GroupSchedule> groupScheduleList;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GroupMember> groupMemberList;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateMembers(List<GroupMember> groupMembers) {
        this.groupMemberList = groupMembers;
    }
}
