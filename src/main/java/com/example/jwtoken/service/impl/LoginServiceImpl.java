package com.example.jwtoken.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.example.jwtoken.dto.req.SignupReq;
import com.example.jwtoken.listener.SignupEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.jwtoken.config.jwt.JwtTokenProvider;
import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.res.JwtTokenRes;
import com.example.jwtoken.entity.User;
import com.example.jwtoken.exception.NoUserInformation;
import com.example.jwtoken.repository.UserRepository;
import com.example.jwtoken.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService
{
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public JwtTokenRes getUserInfoBy(LoginReq loginReq)
	{

		Optional<User> user = userRepository.findByEmail(loginReq.getEmail());

		if (user.isPresent())
		{
			User userInfo = user.get();
			return 	JwtTokenRes.builder()
					.accessToken(jwtTokenProvider.generateAccessToken(jwtTokenProvider.setAuthentication(userInfo.getEmail(), userInfo.getPassword())))
					.refreshToken(jwtTokenProvider.generateRefreshToken(userInfo.getEmail()))
					.expiredTime(jwtTokenProvider.getExpiredTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
					.email(userInfo.getEmail())
					.build();
		}else {
			return null;
		}
	}

	@Override
	public JwtTokenRes signup(SignupReq signupReq) {

		// 1. 회원 가입
		User user = userRepository.save(User.of(signupReq));

		// 2. 이메일 전송
		// 실제 이메일 전송은 아니고, DB에  insert 하면 이메일 전송된다고 가정...
		eventPublisher.publishEvent(SignupEvent.of(signupReq.getEmail()));

		return 	JwtTokenRes.builder()
				.accessToken(jwtTokenProvider.generateAccessToken(jwtTokenProvider.setAuthentication(user.getEmail(), user.getPassword())))
				.refreshToken(jwtTokenProvider.generateRefreshToken(user.getEmail()))
				.expiredTime(jwtTokenProvider.getExpiredTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
				.email(user.getEmail())
				.build();
	}
}
