package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.MemberJoinRequestDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.UserTest;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.UserTestRepository;
import com.coderder.colorMeeting.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class defaultController {

//    private final UserTestRepository userTestRepository;
    private final MemberService memberService;

    // 모든 사람이 접근 가능한 페이지
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody MemberJoinRequestDto requestDto) {
        // 로그인 되어 있으면 돌려보내기 : 스프링 시큐리티에서 해줌!
        return memberService.join(requestDto);
    }
}
