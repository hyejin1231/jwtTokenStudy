package com.example.jwtoken.dto.req;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReq implements Serializable
{
	private String email;
	private String password;

	@Builder
	public LoginReq(String email, String password)
	{
		this.email = email;
		this.password = password;
	}
}
