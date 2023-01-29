package com.coderder.colorMeeting.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MembersRequestDto {
    private List<Long> memberIds;
}
