package com.example.jwtoken.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtTokenRes
{
	private String accessToken;
	private String refreshToken;
	private String expiredTime;
	private String email;

	@Builder
	public JwtTokenRes(String accessToken, String refreshToken, String expiredTime, String email)
	{
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiredTime = expiredTime;
		this.email = email;
	}
}
