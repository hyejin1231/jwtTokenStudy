package com.example.jwtoken.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
	private LocalDateTime createdAt;

	@Builder
	public User(String email, String password, String name)
	{
		this.email = email;
		this.password = password;
		this.name = name;
		this.createdAt = LocalDateTime.now();
	}
}
