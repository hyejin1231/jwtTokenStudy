package com.example.jwtoken.dto.res;

import com.example.jwtoken.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRes
{
	private long userNo;
	private String email;
	private String password;
	private String name;
	private String userRole;

	@Builder
	public LoginRes(long userNo, String email, String password, String name, String userRole)
	{
		this.userNo = userNo;
		this.email = email;
		this.password = password;
		this.name = name;
		this.userRole = userRole;
	}

	public static LoginRes of(User user)
	{
		return LoginRes.builder()
				.userNo(user.getUserNo())
				.email(user.getEmail())
				.password(user.getPassword())
				.name(user.getName())
				.userRole(user.getUserRole())
				.build();
	}
}
