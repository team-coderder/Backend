package com.coderder.colorMeeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class memberController {

    // 모든 사람이 접근 가능한 페이지
    @GetMapping("home")
    public String home() {
        return "home";
    }

    // 로그인한 사람만 접근 가능한 페이지


    // 관리자만 접근 가능한 페이지


    // 회원가입
}
