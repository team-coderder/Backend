package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamDetailResponseDto {
    private Long teamId;
    private String name;
    private String myRole;
    private List<TeamMemberDto> teamMembers;
    private List<InvitationDto> invitations;
}