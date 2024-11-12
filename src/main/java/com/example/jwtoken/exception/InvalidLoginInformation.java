package com.example.jwtoken.exception;

public class InvalidLoginInformation extends RuntimeException
{
	private static final String MESSAGE = "로그인 정보 불일치";

	public InvalidLoginInformation()
	{
		super(MESSAGE);
	}

	public InvalidLoginInformation(String message, Throwable cause)
	{
		super(message, cause);
	}
}
