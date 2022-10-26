package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.MemberJoinRequestDto;
import com.coderder.colorMeeting.dto.response.MemberJoinDto;
import com.coderder.colorMeeting.dto.response.TeamMemberDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String join(MemberJoinRequestDto requestDto) {
        // 로그인되어 있으면 돌려보내기
        // 컨트롤러에서 request.header 체크하여 수행하려 했으나
        // 스프링 시큐리티에서 수행해주는 것으로 확인함

        // username 중복체크하기

        // 디폴트값 넣어주기 (암호화한 비밀번호, 권한 디폴트값)
        System.out.println("회원가입 시작");
        System.out.println("username : " + requestDto.getUsername());
        System.out.println("password : " + requestDto.getPassword());

        Member member = Member.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                .roles("ROLE_USER")
                .build();

        // 저장하기
        memberRepository.save(member);

        return "회원가입 완료";
    }
}
