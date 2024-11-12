package com.example.jwtoken.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.res.LoginRes;
import com.example.jwtoken.entity.User;
import com.example.jwtoken.exception.InvalidLoginInformation;
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
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginRes getUserInfoBy(LoginReq loginReq)
	{
		User user = userRepository.findByEmail(loginReq.getEmail()).orElseThrow(InvalidLoginInformation::new);


		return LoginRes.of(user);
	}
}
