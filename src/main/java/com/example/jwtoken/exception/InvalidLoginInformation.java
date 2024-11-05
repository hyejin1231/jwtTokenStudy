package com.example.jwtoken.exception;

public class InvalidLoginInformation extends RuntimeException
{

	public InvalidLoginInformation()
	{
	}

	public InvalidLoginInformation(String message)
	{
		super(message);
	}

	public InvalidLoginInformation(String message, Throwable cause)
	{
		super(message, cause);
	}
}
