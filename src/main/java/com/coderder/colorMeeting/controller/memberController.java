package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class memberController {


    // 로그인한 사람만 접근 가능한 페이지
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUserTest().getId());
        System.out.println("principal : "+principal.getUserTest().getUsername());
        System.out.println("principal : "+principal.getUserTest().getPassword());

        return "로그인한 사람만 접근 가능한 페이지";
    }

    // 관리자만 접근 가능한 페이지


}
