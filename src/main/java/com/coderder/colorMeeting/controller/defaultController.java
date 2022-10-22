package com.coderder.colorMeeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class defaultController {
    // 모든 사람이 접근 가능한 페이지
    @GetMapping("home")
    public String home() {
        return "home";
    }
}
