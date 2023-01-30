package com.coderder.colorMeeting.dto.response;

import com.coderder.colorMeeting.model.Member;
import lombok.Getter;

//@Builder
@Getter
public class MemberDto {
    private final Long memberId;
    private final String username;
    private final String nickname;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
