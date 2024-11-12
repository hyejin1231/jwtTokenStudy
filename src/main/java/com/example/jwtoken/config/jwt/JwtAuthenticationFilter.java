package com.example.jwtoken.config.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtAuthenticationFilter extends GenericFilter
{
	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider)
	{
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		String jwtToken = getJwtFromRequest((HttpServletRequest) servletRequest);

		if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken))
		{
			Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	// Request Header에서 JWT 토큰 정보 추출
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
