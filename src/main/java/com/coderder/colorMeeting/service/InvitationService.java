package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.response.InvitationListDto;
import com.coderder.colorMeeting.dto.response.ResponseMessage;

public interface InvitationService {
    InvitationListDto showAllInvitations(PrincipalDetails userDetails);

    ResponseMessage createInvitation(PrincipalDetails userDetails, TeamMemberRequestDto requestDto);

    ResponseMessage acceptInvitation(PrincipalDetails userDetails, Long invitationId);

    ResponseMessage refuseInvitation(PrincipalDetails userDetails, Long invitationId);

    ResponseMessage cancelInvitation(PrincipalDetails userDetails, Long invitationId);
}
