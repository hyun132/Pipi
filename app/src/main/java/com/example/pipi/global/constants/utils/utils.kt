package com.example.pipi.global.constants.utils

import java.util.regex.Pattern

fun phoneNumberValidation(phoneNumber: String): Boolean =
    Pattern.matches("^01[016789]\\d{4}\\d{4}", phoneNumber)

/**
 *  입력한 휴대전화 번호값에 따른 에러 메시지 리턴하는 함수.
 *  이미 가입된 번호에 대한 처리는 api error message로 처리
 */
fun phoneNumberErrorMessage(phoneNumber: String): String {
    return if (phoneNumber.isEmpty()) ""
    else if (!phoneNumberValidation(phoneNumber)) "올바른 형식이 아닙니다"
    else if (phoneNumber.length != 11) "휴대전화 번호는 11자로 설정해 주세요"
    else ""
}

fun passwordValidation(password: String): Boolean =
    password.length > 6

fun passwordErrorMessage(password: String): String {
    return if (password.isEmpty()) ""
    else if (!passwordValidation(password)) "올바른 형식이 아닙니다"
    else ""
}