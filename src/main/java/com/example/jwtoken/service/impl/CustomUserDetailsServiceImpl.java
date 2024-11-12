package com.example.jwtoken.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtoken.exception.InvalidLoginInformation;
import com.example.jwtoken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService
{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{

		return createUserDetails(userRepository.findByEmail(username).orElseThrow(InvalidLoginInformation::new));
	}

	private UserDetails createUserDetails(com.example.jwtoken.entity.User user)
	{
		return User.builder()
				.username(user.getEmail())
				.password(passwordEncoder.encode(user.getPassword()))
				.roles(user.getUserRole())
				.build();
	}
}
