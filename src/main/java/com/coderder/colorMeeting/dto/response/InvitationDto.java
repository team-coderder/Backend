package com.coderder.colorMeeting.dto.response;

import java.time.LocalDateTime;

public class InvitationDto {
    private Long invitationId;
    private Long fromMemberId;
    private Long toMemberId;
    private LocalDateTime createdAt;
}
