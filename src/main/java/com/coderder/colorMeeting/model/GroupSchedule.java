package com.coderder.colorMeeting.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group group;

    @Column(nullable = false)
    private String name;

    private String weekday;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String memo;

}