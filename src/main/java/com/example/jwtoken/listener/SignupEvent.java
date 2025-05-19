package com.example.jwtoken.listener;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupEvent {
    private String email;
    private String content;

    @Builder
    public SignupEvent(String email, String content) {
        this.email = email;
        this.content = content;
    }

    public static SignupEvent of(String email) {
        return SignupEvent.builder()
                .email(email)
                .content("[ " +email + " ] 회원 가입 완료")
                .build();
    }
}
