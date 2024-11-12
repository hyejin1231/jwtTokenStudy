package com.example.jwtoken.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.example.jwtoken.config.jwt.JwtAuthenticationEntryPoint;
import com.example.jwtoken.config.jwt.JwtAuthenticationFilter;
import com.example.jwtoken.config.jwt.JwtTokenProvider;

@Configuration
@EnableMethodSecurity
public class SecurityConfig
{

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint)
	{
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Bean
	public Map<String, RequestMatcher> publicEndPointMatcher()
	{
		Map<String, RequestMatcher> requestMatcherMap = new HashMap<>();
		requestMatcherMap.put("ALL", new OrRequestMatcher(
				new AntPathRequestMatcher("/v1/login", "POST"),
				new AntPathRequestMatcher("/h2-console/**/**")

		));

		return requestMatcherMap;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		// CSRF 비활성화 -> REST API의 경우 CSRF 보호 필요 X, 토큰 기반 인증 사용할 경우 세션 사용도 안하므로 적용 X
		// 모든 요청에 JWT 토큰 인증을 수행, REST API는 보통 무상태(STATELESS) 방식을 사용하므로 세션 사용 X
		// header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) -> X-Freame-Options 헤더 비활성화, 현재 페이지 로드 차단
		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								publicEndPointMatcher().get("ALL")
						).permitAll()
						.anyRequest().authenticated()
				)
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
						httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				;

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


}
