package com.coderder.colorMeeting.controller;

import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.MemberUpdateDto;
import com.coderder.colorMeeting.dto.request.TeamRequestDto;
import com.coderder.colorMeeting.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class memberController {

    private final MemberService memberService;

    // 로그인한 사람만 접근 가능한 페이지
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return "로그인한 사람만 접근 가능한 페이지";
    }

    // 관리자만 접근 가능한 페이지

    // 유저 검색 : 닉네임 일부로
    @RequestMapping(value="/search/nickname", method = RequestMethod.GET)
    public ResponseEntity<?> getMembersByNickname(@RequestParam("query") String partOfNickname) {
        return ResponseEntity.ok().body(memberService.getMembersByNickname(partOfNickname));
    }

    // 유저검색 : 정확한 아이디로
    @RequestMapping(value="/search/username/exact", method = RequestMethod.GET)
    public ResponseEntity<?> getMembersByExactUsername(@RequestParam("query") String exactUsername) {
        return ResponseEntity.ok().body(memberService.getMembersByExactUsername(exactUsername));
    }

    // 유저검색 : 아이디 일부로
    @RequestMapping(value="/search/username", method = RequestMethod.GET)
    public ResponseEntity<?> getMembersByUsername(@RequestParam("query") String exactUsername) {
        return ResponseEntity.ok().body(memberService.getMembersByUsername(exactUsername));
    }

    // 내 정보 조회하기
    @RequestMapping(value="/mypage", method= RequestMethod.GET)
    public ResponseEntity<?> getMyInformation(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                @RequestParam("memberId") Long memberId) {
        return ResponseEntity.ok().body(memberService.getMyInformation(userDetails, memberId));
    }

    // 내 정보 수정하기
    @RequestMapping(value="/mypage", method= RequestMethod.PATCH)
    public ResponseEntity<?> updateMyInformation(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                    @RequestParam("memberId") Long memberId, @RequestBody MemberUpdateDto requestDto) {
        return ResponseEntity.ok().body(memberService.updateMyInformation(userDetails, memberId, requestDto));
    }
}
