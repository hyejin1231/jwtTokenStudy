package com.example.jwtoken.common.enums;

import lombok.Getter;

public enum JwtAuthCode
{
	AUTHORITIES_KEY("auth", "인증 키"),
	HEADER_AUTHORIZATION("Authorization", "헤더 인증 키"),
	REDIS_REFRESH_TOKEN("refreshToken", "토큰 갱신");

	@Getter
	final String key;
	final String description;

	JwtAuthCode(String key, String description)
	{
		this.key = key;
		this.description = description;
	}
}
