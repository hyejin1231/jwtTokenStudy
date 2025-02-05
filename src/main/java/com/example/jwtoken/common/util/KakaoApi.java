package com.example.jwtoken.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.jwtoken.entity.KakaoProfile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class KakaoApi
{
	@Value("${kakao.api_key}")
	private String kakaoApiKey;

	@Value("${kakao.redirect_uri}")
	private String kakaoRedirectUri;

	@Value("${kakao.token_uri}")
	private String token_uri;

	@Value("${kakao.info_uri}")
	private String info_uri;

	public String getAccessToken(String code)
	{
		RestTemplate restTemplate = new RestTemplate();

		// HttpHeader Object
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody Object
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoApiKey);
		params.add("redirect_uri", kakaoRedirectUri);
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// reqUrl 로 Http 요청, POST 방식
		ResponseEntity<String> response = restTemplate.exchange(token_uri,
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);

		String responseBody = response.getBody();
		JsonObject asJsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

		return asJsonObject.get("access_token").getAsString();
	}

	public KakaoProfile getUserInfo(String accessToken) {

		RestTemplate restTemplate = new RestTemplate();

		//HttpHeader 오브젝트
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		//http 헤더(headers)를 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

		//reqUrl로 Http 요청 , POST 방식
		ResponseEntity<String> response = restTemplate.exchange(info_uri, HttpMethod.POST, kakaoProfileRequest, String.class);

		return new KakaoProfile(response.getBody());
	}
}
