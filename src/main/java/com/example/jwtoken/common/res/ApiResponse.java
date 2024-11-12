package com.example.jwtoken.common.res;

import com.example.jwtoken.common.enums.ApiMessage;

import lombok.Getter;

@Getter
public class ApiResponse
{
	private int status;
	private String message;
	private Object data;

	public ApiResponse(ApiMessage apiMessage)
	{
		this.status = apiMessage.getStatus();
		this.message = apiMessage.getMessage();
	}

	public ApiResponse(ApiMessage apiMessage, Object data)
	{
		this(apiMessage);
		this.data = data;
	}

	public static ApiResponse success()
	{
		return new ApiResponse(ApiMessage.SUCCESS);
	}

	public static ApiResponse success(Object data)
	{
		return new ApiResponse(ApiMessage.SUCCESS, data);
	}

	public static ApiResponse fail(ApiMessage apiMessage)
	{
		return new ApiResponse(apiMessage);
	}

}
