package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class InvitationDto {
    private Long id;
    private TeamSimpleResponseDto team;
    private MemberDto fromMember;
    private MemberDto toMember;
    private LocalDateTime createdAt;
}
