package com.example.jwtoken.common.enums;

import lombok.Getter;

@Getter
public enum ApiMessage
{
	SUCCESS(200, "성공"),
	BAD_REQUEST(400, "잘못된 요청"),
	UNAUTHORIZED(401, "유효하지 않은 인증"),
	FORBIDDEN(403, "유효하지 않은 접근"),
	NOT_FOUND(404, "찾을 수 없음"),
	SERVER_ERROR(500, "서버 오류")
	;

	final int status;
	final String message;

	ApiMessage(int status, String message)
	{
		this.status = status;
		this.message = message;
	}
}
