package com.coderder.colorMeeting.model;

import javax.persistence.*;

@Entity
public class GroupSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group group;

    @Column(nullable = false)
    private String name;

    private String weekday;
    private String startTime;
    private String finishTime;
    private String memo;
}
