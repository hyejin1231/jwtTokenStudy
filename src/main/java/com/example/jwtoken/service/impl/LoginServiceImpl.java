package com.example.jwtoken.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService
{
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

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
			throw new NoUserInformation();
		}
	}
}
