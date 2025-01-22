package com.example.jwtoken.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**") // 모든 URL 경로에 대해 CORS 정책 적용
				.allowedOriginPatterns("*") // 모든 출처 허용
				.allowedMethods("GET", "POST")  // 허용할 HTTP 메서드 지정
				.allowedHeaders("*") // 요청에 포함된 모든 헤더 적용
				.exposedHeaders("Origin, X-Requested-With, Content-Type, Accept, Authorization") // 클라이언트가 접근할 수 있는 헤더
				.allowCredentials(true) // 인증 정보에 포함한 요청 (쿠키, 세션 ID) 허용
				.maxAge(3600); // CORS 관련 메타데이터를 캐시하는 시간 (초단위, 3600초 = 1시간)
	}
}
