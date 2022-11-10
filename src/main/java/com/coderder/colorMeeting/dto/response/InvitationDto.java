package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InvitationDto {
    private Long invitationId;
    private Long fromTeamId;
    private Long fromMemberId;
    private Long toMemberId;
    private LocalDateTime createdAt;
}
