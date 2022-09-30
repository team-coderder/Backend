package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.ResponseMessage;
import com.coderder.colorMeeting.dto.request.GroupRequestDto;
import com.coderder.colorMeeting.exception.ErrorHandler;
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
    public ResponseEntity<?> createGroup(@RequestBody GroupRequestDto requestDto) {
        ResponseMessage<?> data = groupService.createGroup(requestDto);
        return ErrorHandler.returnResponse(data);
    }
}
