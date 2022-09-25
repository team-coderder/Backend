package com.coderder.colorMeeting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class PersonalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    private String name;

    private String weekday;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String memo;

}
