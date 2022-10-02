package com.coderder.colorMeeting.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@IdClass(GroupMemberId.class)
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group group;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private GroupRole groupRole;
}
