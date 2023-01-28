package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.service.InvitationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
public class InvitationController {

    private final InvitationServiceImpl invitationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> showAllInvitations(@AuthenticationPrincipal PrincipalDetails userDetails) {
        return ResponseEntity.ok().body(invitationService.showAllInvitations(userDetails));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createInvitation(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestBody TeamMemberRequestDto requestDto) {
        return ResponseEntity.ok().body(invitationService.createInvitation(userDetails, requestDto));
    }

    @RequestMapping(value = "/accept", method = RequestMethod.PATCH)
    public ResponseEntity<?> acceptInvitation(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long invitationId) {
        return ResponseEntity.ok().body(invitationService.acceptInvitation(userDetails, invitationId));
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.PATCH)
    public ResponseEntity<?> refuseInvitation(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long invitationId) {
        return ResponseEntity.ok().body(invitationService.refuseInvitation(userDetails, invitationId));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.PATCH)
    public ResponseEntity<?> cancelInvitation(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long invitationId) {
        return ResponseEntity.ok().body(invitationService.cancelInvitation(userDetails, invitationId));
    }
}
