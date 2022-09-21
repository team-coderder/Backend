package com.coderder.colorMeeting.model;

import javax.persistence.*;

@Entity
public class PersonalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    private String name;
    private String memo;
    private String weekday;
    private String startTime;
    private String finishTime;

}
