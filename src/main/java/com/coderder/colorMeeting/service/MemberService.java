package com.coderder.colorMeeting.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coderder.colorMeeting.config.jwt.JwtProperties;
import com.coderder.colorMeeting.dto.request.LoginRequestDto;
import com.coderder.colorMeeting.dto.request.MemberJoinRequestDto;
import com.coderder.colorMeeting.dto.request.MemberUpdateDto;
import com.coderder.colorMeeting.dto.response.MemberDto;
import com.coderder.colorMeeting.dto.response.MemberResponseDto;
import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.BadRequestException;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.ErrorResponse;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.coderder.colorMeeting.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    public MemberDto join(MemberJoinRequestDto requestDto) {
        // 로그인되어 있으면 돌려보내기
        // 컨트롤러에서 request.header 체크하여 수행하려 했으나
        // 스프링 시큐리티에서 수행해주는 것으로 확인함

        // username 중복체크하기
        boolean duplicatedUsername = checkDuplicatedUsername(requestDto.getUsername());
        if ( duplicatedUsername == true ) {
//            throw new ErrorResponse(USERNAME_ALREADY_EXISTS);
            throw new BadRequestException(USERNAME_ALREADY_EXISTS);
        }

        // 디폴트값 넣어주기 (암호화한 비밀번호, 권한 디폴트값)
//        System.out.println("회원가입 시작");
//        System.out.println("username : " + requestDto.getUsername());
//        System.out.println("password : " + requestDto.getPassword());

        Member member = Member.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                .roles("ROLE_USER")
                .build();

        // 저장하기
        Member savedMember = memberRepository.save(member);
        return new MemberDto(savedMember);
    }

    private Member isPresentMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElse(null);
    }

    private Member isPresentMemberByUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        return member;
    }

    private boolean checkDuplicatedUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member != null) {
            return true;
        } else {
            return false;
        }
    }

    public MemberResponseDto getMembersByNickname(String partOfNickname) {

        List<Member> memberList = memberRepository.findByNicknameContaining(partOfNickname);

        // TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (Member member : memberList) {
            memberDtoList.add(new MemberDto(member));
        }

        MemberResponseDto response = MemberResponseDto.builder()
                .members(memberDtoList)
                .build();

        return response;
    }


    public Member getMembersByExactUsername(String exactUsername) {
        Member member = memberRepository.findByUsername(exactUsername);

        return member;
    }


    public MemberResponseDto getMembersByUsername(String partOfUsername) {

        List<Member> memberList = memberRepository.findByUsernameContaining(partOfUsername);

        // TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (Member member : memberList) {
            memberDtoList.add(new MemberDto(member));
        }

        MemberResponseDto response = MemberResponseDto.builder()
                .members(memberDtoList)
                .build();

        return response;
    }

    public MemberDto getMyInformation(Long memberId) {

        Member member = isPresentMember(memberId);
        if (member == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        return new MemberDto(member);
    }

    public MemberDto updateMyInformation(Long memberId, MemberUpdateDto memberUpdateDto) {
        // 존재하는 member인가?
        Member member = isPresentMember(memberId);
        if (member == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        // 닉네임 업데이트
        member.updateNickname(memberUpdateDto.getNickname());

        // 비밀번호 업데이트
        // member.updateNickname(memberUpdateDto.getPassword());

        return new MemberDto(member);
    }

    public MemberDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        // username으로 사용자를 찾는다
        String username = requestDto.getUsername();
        Member member = isPresentMemberByUsername(username);
        // 못 찾으면 예외 처리
        if (member == null) {
            System.out.println("========================= 못 찾겠따");
            throw new NotFoundException(MEMBER_NOT_FOUND);
//            throw new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND);

        }

        // 비밀번호가 맞는지 검증한다
        if(!member.validatePassword(passwordEncoder, requestDto.getPassword())){
            System.out.println("========================= 비밀번호 틀림");
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        // 토큰을 발급한다
        String jwtToken = JWT.create()
                .withSubject(username) // 토큰 이름. 크게 의미 없음
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtProperties.EXPIRATION_TIME)) // 만료시간
                .withClaim("id", member.getId()) // payload : id. pk
                .withClaim("username", member.getUsername()) // payload : username. pk
                .sign(Algorithm.HMAC512(jwtProperties.getSECRET())); // 검증값

        response.addHeader(jwtProperties.HEADER_STRING, jwtProperties.TOKEN_PREFIX+jwtToken); // 헤더key, value

        // 응답한다
        return new MemberDto(member);
    }



    public String logout(HttpServletRequest request) {
        return "로그아웃 되었습니다";
    }
}
