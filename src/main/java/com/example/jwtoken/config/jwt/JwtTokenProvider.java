package com.example.jwtoken.config.jwt;

import static com.example.jwtoken.common.enums.JwtAuthErrorMessage.JTW_INVALID_TOKEN;
import static com.example.jwtoken.common.enums.JwtAuthErrorMessage.JWT_AUTHENTICATION_NOT_VALID;
import static com.example.jwtoken.common.enums.JwtAuthErrorMessage.JWT_CLAIMS_EMPTY;
import static com.example.jwtoken.common.enums.JwtAuthErrorMessage.JWT_EXPIRED_TOKEN;
import static com.example.jwtoken.common.enums.JwtAuthErrorMessage.JWT_UNSUPPORTED_TOKEN;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.jwtoken.common.enums.JwtAuthCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider
{

	@Value("${jwt.access.expired}")
	private long accessTokenExpired;

	@Value("${jwt.refresh.expired}")
	private long refreshTokenExpired;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final Key key;

	public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey, AuthenticationManagerBuilder authenticationManagerBuilder)
	{
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}


	/**
	 * 정보 가지고 AccessToken 생성 메서드
	 * @param authentication
	 * @return
	 */
	public String generateAccessToken(Authentication authentication)
	{
		// 0. 현재 인증된 사용자의 권한 정보 문자열 반환
		String authorities = getUserAuthToString(authentication);

		Date accessTokenExpiresIn = getExpiredDate(accessTokenExpired);

		return Jwts.builder()
				.setSubject(authentication.getName()) // 클레임에 현재 인증된 사용자 이름(id) 설정
				.claim(JwtAuthCode.AUTHORITIES_KEY.getKey(), authorities) // 추가 클레임으로 사용자 권한 정보
				.setExpiration(accessTokenExpiresIn) // 토큰 만료 시간
				.signWith(key, SignatureAlgorithm.HS256) // 토큰에 서명 추가, HMAC-SHA256 알고리즘 사용, 토큰 진위 검증할 때 지정
				.compact(); // 설정한 정보 바탕으로 JWT 생성 -> 문자열 반환
	}


	/**
	 * RefreshToken 생성 메서드
	 * @param id
	 * @return
	 */
	public String generateRefreshToken(String id)
	{
		Date now = new Date();
		Date refreshTokenExpiresIn = getExpiredDate(refreshTokenExpired);

		return Jwts.builder()
				.setIssuedAt(now) // IssuedAt : 클레임 JWT 발행 시간, 발행 시간 기반으로 토큰 재발행 상황 판단
				.setExpiration(refreshTokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * JWT 토큰 복호화해서 토큰에 들어있는 정보 조회
	 * @param accessToken
	 * @return
	 */
	public Authentication getAuthentication(String accessToken)
	{
		// JWT 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null)
		{
			throw new IllegalArgumentException(JWT_AUTHENTICATION_NOT_VALID.getMessage());
		}

		// auth 클레임에서 권한 정보 가져오기
		List<SimpleGrantedAuthority> authorities = Arrays.stream(
						claims.get(JwtAuthCode.AUTHORITIES_KEY.getKey()).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public Authentication setAuthentication(String id, String password)
	{
		Authentication authentication = null;

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

		// authenticate 메서드를 통해 인증 검증 진행
		// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
		authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		return authentication;
	}

	/**
	 * AccessToken 복호화
	 * @param accessToken
	 * @return
	 */
	public Claims parseClaims(String accessToken)
	{
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
	}

	/**
	 * 인증된 사용자의 권한을 문자열로 변환해 가져오는 메서드
	 * @param authentication
	 * @return
	 */
	private static String getUserAuthToString(Authentication authentication)
	{
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}

	/**
	 * AccessToken 만료일 조회
	 * @return
	 */
	private Date getExpiredDate(long expiredDuration)
	{
		long now = new Date().getTime();
		return new Date(now + expiredDuration);
	}

	public LocalDateTime getExpiredTime()
	{
		LocalDateTime now = LocalDateTime.now();
		return now.plusSeconds(accessTokenExpired / 1000);
	}

	/**
	 * JWT 토큰 검증
	 * @param jwtToken
	 * @return
	 */
	public boolean validateToken(String jwtToken)
	{
		try
		{
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwtToken);
			return true;
		}
		catch (SecurityException | MalformedJwtException e)
		{
			log.info(JTW_INVALID_TOKEN.getMessage(), e);
		}
		catch (ExpiredJwtException e)
		{
			log.info(JWT_EXPIRED_TOKEN.getMessage(), e);
		}
		catch (UnsupportedJwtException e)
		{
			log.info(JWT_UNSUPPORTED_TOKEN.getMessage(), e);
		}
		catch (IllegalArgumentException e)
		{
			log.info(JWT_CLAIMS_EMPTY.getMessage(), e);
		}
		return false;
	}
}
