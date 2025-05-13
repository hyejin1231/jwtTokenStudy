package com.example.jwtoken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class JwtokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtokenApplication.class, args);
	}

}
