package com.coderder.colorMeeting.util;

import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ForbiddenException;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.Team;
import com.coderder.colorMeeting.model.TeamMember;
import com.coderder.colorMeeting.repository.InvitationRepository;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamMemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;

import static com.coderder.colorMeeting.exception.ErrorCode.*;
import static com.coderder.colorMeeting.exception.ErrorCode.NO_PERMISSION_FOR_THIS_REQUEST;
import static com.coderder.colorMeeting.model.TeamRole.LEADER;

public class DataFinder {

    private TeamRepository teamRepository;
    private MemberRepository memberRepository;
    private TeamMemberRepository teamMemberRepository;
    private InvitationRepository invitationRepository;

    public Team findTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
        return team;
    }

    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
        return member;
    }

    public TeamMember findTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember == null) {
            throw new NotFoundException(TEAM_MEMBER_NOT_FOUND);
        }
        return teamMember;
    }

    public void checkLeaderRole(TeamMember teamMember) {
        if (teamMember.getTeamRole() != LEADER) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }

    public void checkTeamMember(Member member, Team team) {
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember != null) {
            throw new BadRequestException(ALREADY_TEAM_MEMBER);
        }
    }

    public void checkSameMember(Member member1, Member member2) {
        if (member1.getId() != member2.getId()) {
            throw new ForbiddenException(NO_PERMISSION_FOR_THIS_REQUEST);
        }
    }

}
