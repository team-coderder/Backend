package com.coderder.colorMeeting.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 에러가 없을 시
    NULL("NO_ERROR", "에러 없음"),

    // 예기치 못한 에러
    UNKNOWN_ERROR("UNKNOWN_ERROR", "예기치 못한 오류가 발생하였습니다."),

    // Team 관련 오류
    TEAM_NOT_FOUND("TEAM_NOT_FOUND", "해당 그룹(팀)을 찾을 수 없습니다."),
    INVALID_TEAM_NAME("INVALID_TEAM_NAME", "유효하지 않은 그룹(팀) 이름입니다."),

    // 회원 관련 오류
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다."),

    // TeamMember 관련 오류
    TEAM_MEMBER_NOT_FOUND("TEAM_MEMBER_NOT_FOUND", "해당 그룹(팀)에서 회원을 조회할 수 없습니다. 해당 그룹(팀)의 회원이 아닙니다."),

    // 관리자가 아닌데 수정, 삭제 요청 등 권한 오류
    NOT_AUTHORIZED("NOT_AUTHORIZED", "권한이 없습니다."),

    // 회원가입 로직 : 중복 아이디
    USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "이미 존재하는 유저입니다");



    private final String code;
    private final String message;
}
