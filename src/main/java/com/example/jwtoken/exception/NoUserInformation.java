package com.example.jwtoken.exception;

public class NoUserInformation extends RuntimeException
{
	private static final String MESSAGE = "회원가입 정보 없음";

	public NoUserInformation()
	{
		super(MESSAGE);
	}

	public NoUserInformation(String message, Throwable cause)
	{
		super(message, cause);
	}
}
