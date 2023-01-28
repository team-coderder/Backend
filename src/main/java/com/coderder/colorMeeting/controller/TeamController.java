package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createTeam(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestBody TeamRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.createTeam(userDetails, requestDto));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> showTeamInfo(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.showTeamInfo(userDetails, teamId));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> updateTeam(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId, @RequestBody TeamRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.updateTeam(userDetails, teamId, requestDto));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeam(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.deleteTeam(userDetails, teamId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamMembers(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.getTeamMembers(userDetails, teamId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.PATCH)
    public ResponseEntity<?> addMember(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestBody TeamMemberRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.addMember(userDetails, requestDto));
    }

    @RequestMapping(value = "/members", method = RequestMethod.DELETE)
    public ResponseEntity<?> memberOut(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId, Long memberId) {
        return ResponseEntity.ok().body(teamService.memberOut(userDetails, teamId, memberId));
    }

    @RequestMapping(value = "/myteams", method = RequestMethod.GET)
    public ResponseEntity<?> getMyTeams(@AuthenticationPrincipal PrincipalDetails userDetails) {
        return ResponseEntity.ok().body(teamService.getMyTeams(userDetails));
    }

    @RequestMapping(value = "/myteam", method = RequestMethod.DELETE)
    public ResponseEntity<?> leaveTeam(@AuthenticationPrincipal PrincipalDetails userDetails, @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.leaveTeam(userDetails, teamId));
    }
}
