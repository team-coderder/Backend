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

    // Group 관련 오류
    GROUP_NOT_FOUND("GROUP_NOT_FOUND", "해당 그룹을 찾을 수 없습니다."),

    // 관리자가 아닌데 수정, 삭제 요청 등 권한 오류
    NOT_AUTHORIZED("NOT_AUTHORIZED", "권한이 없습니다.");


    private final String code;
    private final String message;
}
