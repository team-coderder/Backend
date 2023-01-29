package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.response.InvitationDto;
import com.coderder.colorMeeting.dto.response.MemberDto;
import com.coderder.colorMeeting.dto.response.TeamMemberDto;
import com.coderder.colorMeeting.dto.response.TeamSimpleResponseDto;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Invitation;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamMember;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.coderder.colorMeeting.exception.ErrorCode.*;
import static com.coderder.colorMeeting.exception.ErrorCode.NO_PERMISSION_FOR_THIS_REQUEST;
import static com.coderder.colorMeeting.model.TeamRole.LEADER;

@Service
@RequiredArgsConstructor
public class CommonService {

    final TeamRepository teamRepository;
    final MemberRepository memberRepository;
    final TeamMemberRepository teamMemberRepository;
    final InvitationRepository invitationRepository;

    protected Team findTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
        return team;
    }

    protected Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
        return member;
   }
   
   protected TeamMember findTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember == null) {
            throw new NotFoundException(TEAM_MEMBER_NOT_FOUND);
        }
        return teamMember;
    }
    
    protected void checkLeaderRole(TeamMember teamMember) {
        if (teamMember.getTeamRole() != LEADER) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }
    
    protected void checkTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember != null) {
            throw new BadRequestException(ALREADY_TEAM_MEMBER);
        }
    }

    protected Invitation findInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException(INVITATION_NOT_FOUND));
        return invitation;
    }

    protected void checkInvitation(Member targetMember, Team targetTeam) {
        Invitation invitation = invitationRepository.findByToMemberAndFromTeam(targetMember, targetTeam);
        if (invitation != null) {
            throw new BadRequestException(ALREADY_INVITED);
        }
    }

    protected void checkSameMember(Member member1, Member member2) {
        if (member1.getId() != member2.getId()) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }

    protected List<TeamMemberDto> buildTeamMemberDtos(List<TeamMember> teamMembers) {
        List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Member member = teamMember.getMember();
            teamMemberDtos.add(TeamMemberDto.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .teamRole(String.valueOf(teamMember.getTeamRole()))
                    .build());
        }
        return teamMemberDtos;
    }

    protected List<InvitationDto> buildInvitationDtos(List<Invitation> invitations) {
        List<InvitationDto> invitationDtos = new ArrayList<>();
        for (Invitation invitation : invitations) {
            invitationDtos.add(InvitationDto.builder()
                    .id(invitation.getId())
                    .team(new TeamSimpleResponseDto(invitation.getFromTeam()))
                    .fromMember(new MemberDto(invitation.getFromLeader()))
                    .toMember(new MemberDto(invitation.getToMember()))
                    .createdAt(invitation.getCreatedAt())
                    .build()
            );
        }
        return invitationDtos;
    }

    protected List<TeamSimpleResponseDto> buildTeamSimpleResponseDto(List<TeamMember> teamMembers) {
        List<TeamSimpleResponseDto> data = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            Team team = teamMember.getTeam();
            data.add(new TeamSimpleResponseDto(team));
        }
        return data;
    }
}
