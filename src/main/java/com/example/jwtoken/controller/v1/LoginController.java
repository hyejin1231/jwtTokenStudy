package com.example.jwtoken.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtoken.common.res.ApiResponse;
import com.example.jwtoken.controller.BaseController;
import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.res.JwtTokenRes;
import com.example.jwtoken.dto.res.LoginRes;
import com.example.jwtoken.service.LoginService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LoginController extends BaseController
{
	private final LoginService loginService;
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginReq req)
	{
		// TODO 유효성 검증 필요
		JwtTokenRes jwtTokenRes = loginService.getUserInfoBy(req);
		log.info("[user login] email : {}", jwtTokenRes.getEmail());
		return resSuccess(jwtTokenRes);
	}

	@GetMapping("/test")
	public ResponseEntity<ApiResponse> test()
	{
		log.info("test");
		return resSuccess(null);
	}

}
