package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.TeamMemberRequestDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.dto.response.*;

import javax.transaction.Transactional;

public interface TeamService {
    @Transactional
    TeamSimpleResponseDto createTeam(PrincipalDetails userDetails, TeamRequestDto requestDto);

    @Transactional
    TeamDetailResponseDto showTeamInfo(PrincipalDetails userDetails, Long teamId);

    @Transactional
    TeamSimpleResponseDto updateTeam(PrincipalDetails userDetails, Long teamId, TeamRequestDto requestDto);

    @Transactional
    ResponseMessage deleteTeam(PrincipalDetails userDetails, Long teamId);

    @Transactional
    TeamMembersResponseDto getTeamMembers(PrincipalDetails userDetails, Long teamId);

    @Transactional
    ResponseMessage addMember(PrincipalDetails userDetails, TeamMemberRequestDto requestDto);

    @Transactional
    ResponseMessage memberOut(PrincipalDetails userDetails, TeamMemberRequestDto requestDto);

    @Transactional
    TeamResponseDto getMyTeams(PrincipalDetails userDetails);

    @Transactional
    ResponseMessage leaveTeam(PrincipalDetails userDetails, Long teamId);
}
