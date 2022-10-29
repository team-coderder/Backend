package com.coderder.colorMeeting.service;

import com.coderder.colorMeeting.dto.request.MemberJoinRequestDto;
import com.coderder.colorMeeting.dto.response.MemberResponseDto;
import com.coderder.colorMeeting.exception.ErrorResponse;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.coderder.colorMeeting.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.coderder.colorMeeting.exception.ErrorCode.USERNAME_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberResponseDto join(MemberJoinRequestDto requestDto) {
        // 로그인되어 있으면 돌려보내기
        // 컨트롤러에서 request.header 체크하여 수행하려 했으나
        // 스프링 시큐리티에서 수행해주는 것으로 확인함

        // username 중복체크하기
        boolean duplicatedUsername = checkDuplicatedUsername(requestDto.getUsername());
        if ( duplicatedUsername == true ) {
            throw new ErrorResponse(USERNAME_ALREADY_EXISTS);
        }

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
        Member savedMember = memberRepository.save(member);

        MemberResponseDto responseDto = MemberResponseDto.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .nickname(savedMember.getNickname())
                .build();

        return responseDto;
    }

    public List<MemberResponseDto> getMembers(String partOfNickname) {
        List<Member> memberList = memberRepository.findByNicknameContaining(partOfNickname);

        // TeamMembers라는 객체에서 각 멤버들에 대한 정보 추출하기
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();

        for (Member member : memberList) {
            MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .build();
            memberResponseDtoList.add(memberResponseDto);
        }

        return memberResponseDtoList;
    }

    public MemberResponseDto getMyInformation(Long memberId) {

        Member member = isPresentMember(memberId);
        if (member == null) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

        return memberResponseDto;
    }

    private Member isPresentMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElse(null);
    }

    private boolean checkDuplicatedUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member != null) {
            return true;
        } else {
            return false;
        }
    }
}
