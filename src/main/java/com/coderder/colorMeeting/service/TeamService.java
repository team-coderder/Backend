package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.MembersRequestDto;
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
    ResponseMessage addMember(PrincipalDetails userDetails, Long teamId, MembersRequestDto requestDto);

    @Transactional
    ResponseMessage memberOut(PrincipalDetails userDetails, Long teamId, Long memberId);

    @Transactional
    TeamResponseDto getMyTeams(PrincipalDetails userDetails);

    @Transactional
    ResponseMessage leaveTeam(PrincipalDetails userDetails, Long teamId);
}
