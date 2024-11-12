package com.example.jwtoken.common.enums;

import lombok.Getter;

@Getter
public enum UserRole
{
	USER("RS001","일반 사용자"),
	ADMIN("RS002","관리자");

	final String key;
	final String description;

	UserRole(String key, String description)
	{
		this.key = key;
		this.description = description;
	}
}
