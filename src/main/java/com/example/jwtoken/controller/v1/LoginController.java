package com.example.jwtoken.controller.v1;

import com.example.jwtoken.common.enums.ApiMessage;
import com.example.jwtoken.dto.req.SignupReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtoken.common.res.ApiResponse;
import com.example.jwtoken.common.util.KakaoApi;
import com.example.jwtoken.controller.BaseController;
import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.res.JwtTokenRes;
import com.example.jwtoken.entity.KakaoProfile;
import com.example.jwtoken.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LoginController extends BaseController
{
	private final LoginService loginService;
	private final KakaoApi kakaoApi;

	@GetMapping("/test")
	public ResponseEntity<ApiResponse> test()
	{
		log.info("test");
		return resSuccess(null);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginReq req)
	{
		// TODO 유효성 검증 필요
		JwtTokenRes jwtTokenRes = loginService.getUserInfoBy(req);
		log.info("[user login] email !!!!: {}", jwtTokenRes.getEmail());
		return resSuccess(jwtTokenRes);
	}

	@GetMapping("/login/kakao")
	public ResponseEntity<ApiResponse> kakaoLogin(@RequestParam(value = "code") String code)
	{
		// 2. 클라이언트로부터 받은 인가 코드로 액세스 코드 발급
		String accessToken = kakaoApi.getAccessToken(code);

		// 3. 사용자 정보 받기
		KakaoProfile userInfo = kakaoApi.getUserInfo(accessToken);

		// 4. 회원 정보 조회
		LoginReq req = LoginReq.builder().email(String.valueOf(userInfo.getId())).password("1234").build();
		JwtTokenRes jwtTokenRes = loginService.getUserInfoBy(req);

		log.info("userInfo.id() = {}", userInfo.getId());
		log.info("userInfo.getNickname() = {}" , userInfo.getNickname());
		log.info("accessToken = {}" , accessToken);

		return resSuccess(jwtTokenRes);
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody SignupReq req) {

		if (loginService.getUserInfoBy(new LoginReq(req.getEmail(), req.getPassword())) == null) {
			resOk(ApiMessage.DUPLICATE);
		}

		JwtTokenRes jwtTokenRes = loginService.signup(req);
		return resSuccess(jwtTokenRes);
	}

}
