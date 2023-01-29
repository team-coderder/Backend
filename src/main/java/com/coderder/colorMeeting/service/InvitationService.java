package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.response.InvitationListDto;
import com.coderder.colorMeeting.dto.response.ResponseMessage;
import com.coderder.colorMeeting.dto.response.*;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.*;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface InvitationService {
    InvitationListDto showAllInvitations(PrincipalDetails userDetails);

    ResponseMessage createInvitation(PrincipalDetails userDetails, TeamMemberRequestDto requestDto);

    ResponseMessage acceptInvitation(PrincipalDetails userDetails, Long invitationId);

    ResponseMessage refuseInvitation(PrincipalDetails userDetails, Long invitationId);

    ResponseMessage cancelInvitation(PrincipalDetails userDetails, Long invitationId);
}
