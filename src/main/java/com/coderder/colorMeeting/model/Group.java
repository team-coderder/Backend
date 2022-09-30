package com.coderder.colorMeeting.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Entity
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

//    @OneToMany(mappedBy = "group")
//    private List<PersonalSchedule> personalSchedulesList;

    @OneToMany(mappedBy = "group")
    private List<GroupMembers> groupMembersList;

    public void updateName(String name) {
        this.name = name;
    }
}
