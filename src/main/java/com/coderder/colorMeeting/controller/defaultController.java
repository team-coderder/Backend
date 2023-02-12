package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.dto.request.LoginRequestDto;
import com.coderder.colorMeeting.dto.request.MemberJoinRequestDto;
import com.coderder.colorMeeting.dto.response.MemberDto;
import com.coderder.colorMeeting.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class defaultController {

    private final MemberService memberService;

    // 모든 사람이 접근 가능한 페이지
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // 회원가입
    @PostMapping("/join")
    public MemberDto join(@RequestBody MemberJoinRequestDto requestDto) {
        // 로그인 되어 있으면 돌려보내기 : 스프링 시큐리티에서 안 해줌. 로직 추가해야 함.
        return memberService.join(requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public MemberDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }


    // 로그아웃 : refresh token 구현 시 확장성을 위해 구현
//    @RequestMapping(value="/logout", method = RequestMethod.POST)
//    public String logout(HttpServletRequest request) {
//        return memberService.logout(request);
//    }
}
