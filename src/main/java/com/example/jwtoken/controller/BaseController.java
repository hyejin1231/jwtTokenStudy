package com.example.jwtoken.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.jwtoken.common.res.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController
{

	protected ResponseEntity<ApiResponse> resSuccess(Object data)
	{
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ApiResponse.success(data));
	}
}
