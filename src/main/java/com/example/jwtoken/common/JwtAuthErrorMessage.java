package com.example.jwtoken.common;

import lombok.Getter;

public enum JwtAuthErrorMessage
{
	JWT_AUTHENTICATION_NOT_VALID("권한 정보가 없는 토큰"),
	JTW_INVALID_TOKEN("Invalid JWT Token"),
	JWT_EXPIRED_TOKEN("Expired JWT Token"),
	JWT_UNSUPPORTED_TOKEN("Unsupported JWT Token"),
	JWT_CLAIMS_EMPTY("JWT claims string is empty.");

	@Getter
	final String message;

	JwtAuthErrorMessage(String message)
	{
		this.message = message;
	}
}
