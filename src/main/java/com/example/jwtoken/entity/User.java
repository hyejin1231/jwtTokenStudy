package com.example.jwtoken.entity;

import java.time.LocalDateTime;

import com.example.jwtoken.common.enums.UserRole;

import com.example.jwtoken.dto.req.SignupReq;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	private String email;
	private String password;
	private String name;
	private String userRole;
	private LocalDateTime createdAt;

	@Builder
	public User(String email, String password, String name, String userRole)
	{
		this.email = email;
		this.password = password;
		this.name = name;
		this.userRole = userRole;
		this.createdAt = LocalDateTime.now();
	}

	public static User of(SignupReq signupReq) {
		return User.builder()
				.email(signupReq.getEmail())
				.password(signupReq.getPassword())
				.name(signupReq.getName())
				.userRole("USER")
				.build();
	}
}
