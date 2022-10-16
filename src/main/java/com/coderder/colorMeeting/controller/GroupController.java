package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.GroupMemberRequestDto;
import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGroup( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody GroupRequestDto requestDto) {
        return ResponseEntity.ok().body(groupService.createGroup(requestDto));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> updateGroup( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                        @RequestParam Long groupId, @RequestBody GroupRequestDto requestDto) {
        return ResponseEntity.ok().body(groupService.updateGroup(groupId, requestDto));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGroup( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestParam Long groupId) {
        return ResponseEntity.ok().body(groupService.deleteGroup(groupId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ResponseEntity<?> getGroupMembers( // @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long groupId) {
        return ResponseEntity.ok().body(groupService.getGroupMembers(groupId));
    }

    @RequestMapping(value = "/members", method = RequestMethod.PATCH)
    public ResponseEntity<?> addMember(@RequestBody GroupMemberRequestDto requestDto) {
        return ResponseEntity.ok().body(groupService.addMember(requestDto));
    }
}
