package com.example.jwtoken.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

@Getter
public class KakaoProfile
{
	private Long id; // 카카오 회원 번호
	private LocalDateTime connectedAt; // 서비스에 연결된 시각
	private String email;
	private String nickname; // 사용자 properties의 nickname

	public KakaoProfile(String jsonResponseBody){
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonResponseBody);
		System.out.println("jsonResponseBody = " + jsonResponseBody);

		this.id = Long.valueOf(element.getAsJsonObject().get("id").getAsString());

		String connected_at = element.getAsJsonObject().get("connected_at").getAsString();
		connected_at = connected_at.substring(0, connected_at.length() - 1);
		LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		this.connectedAt = connectDateTime;

		JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
		this.nickname = properties.getAsJsonObject().get("nickname").getAsString();
	}
}
