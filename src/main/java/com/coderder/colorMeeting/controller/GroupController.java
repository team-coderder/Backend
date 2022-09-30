package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto<?> createGroup( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody GroupRequestDto requestDto) {
        return groupService.createGroup(requestDto);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseDto<?> updateGroup( // @AuthenticationPrincipal UserDetailsImpl userDetails,
                        @RequestParam Long groupId, @RequestBody GroupRequestDto requestDto) {
        return groupService.updateGroup(groupId, requestDto);
    }
}
