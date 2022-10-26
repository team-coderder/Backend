package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.UserTest;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.UserTestRepository;
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
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 모든 사람이 접근 가능한 페이지
    @GetMapping("home")
    public String home() {
        return "home";
    }

    // 회원가입
    @PostMapping("join")
    public String join(@RequestBody Member member) {
        System.out.println("회원가입 시작");
        System.out.println("username : " + member.getUsername());
        System.out.println("password : " + member.getPassword());
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return "회원가입완료";
    }
}
