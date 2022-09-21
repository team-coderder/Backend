package com.coderder.colorMeeting.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;


@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="group_id")
    private Collection<GroupSchedule> groupSchedulesList;

}
