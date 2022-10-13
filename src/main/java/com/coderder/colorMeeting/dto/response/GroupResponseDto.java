package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupResponseDto {
    private Long groupId;
    private String name;
}
