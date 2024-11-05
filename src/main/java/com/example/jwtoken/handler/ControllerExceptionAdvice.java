package com.example.jwtoken.handler;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.jwtoken.common.enums.ApiMessage;
import com.example.jwtoken.common.res.ApiResponse;
import com.example.jwtoken.exception.InvalidLoginInformation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice
{

	@ExceptionHandler(InvalidLoginInformation.class)
	public ResponseEntity<ApiResponse> invalidLoginInformationHandler(InvalidLoginInformation e)
	{
		log.error("[invalidLoginInformation] : {}", e.getMessage(), e);
		return ResponseEntity.badRequest()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ApiResponse.fail(ApiMessage.BAD_REQUEST));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> exceptionHandler(Exception e)
	{
		log.error("[Server Error Exception] : {} ", e.getMessage(), e);
		return ResponseEntity.internalServerError()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ApiResponse.fail(ApiMessage.SERVER_ERROR));
	}
}
