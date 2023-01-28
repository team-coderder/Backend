package com.coderder.colorMeeting.dto.response;

import com.coderder.colorMeeting.model.Member;
import lombok.Builder;
import lombok.Getter;

//@Builder
@Getter
public class MemberDto {
    private final Long id;
    private final String username;
    private final String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
