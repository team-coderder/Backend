package com.coderder.colorMeeting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PersonalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column()
    private String memo;
    @Column()
    private String weekday;
    @Column()
    private String startTime;
    @Column()
    private String finishTime;

}
