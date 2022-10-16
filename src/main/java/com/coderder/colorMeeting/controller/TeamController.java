package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createTeam( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody TeamRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.createTeam(requestDto));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> updateTeam( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                        @RequestParam Long teamId, @RequestBody TeamRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.updateTeam(teamId, requestDto));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeam( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.deleteTeam(teamId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamMembers( // @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long teamId) {
        return ResponseEntity.ok().body(teamService.getTeamMembers(teamId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.PATCH)
    public ResponseEntity<?> addMember(@RequestBody TeamMemberRequestDto requestDto) {
        return ResponseEntity.ok().body(teamService.addMember(requestDto));
    }

    @RequestMapping(value = "/myteams", method = RequestMethod.GET)
    public ResponseEntity<?> getMyTeams() {
        return ResponseEntity.ok().body(teamService.getMyTeams());
    }


}