package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InvitationListDto {
    private List<InvitationDto> invitations;
}
