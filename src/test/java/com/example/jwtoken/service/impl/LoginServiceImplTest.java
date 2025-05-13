package com.example.jwtoken.service.impl;

import com.example.jwtoken.dto.req.SignupReq;
import com.example.jwtoken.dto.res.JwtTokenRes;
import com.example.jwtoken.entity.User;
import com.example.jwtoken.repository.EmailRepository;
import com.example.jwtoken.repository.UserRepository;
import com.example.jwtoken.service.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceImplTest {

    @Mock
    LoginService loginServiceMock;

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailRepository emailRepository;

    @Test
    public void 정상_회원가입() {
        // given
        SignupReq test = SignupReq.builder()
                .email("test@test.com")
                .password("4567")
                .name("Test")
                .build();

        // when
        JwtTokenRes signup = loginService.signup(test);

        // then
        assertThat(signup).isNotNull();
        assertThat(userRepository.findByEmail("test@test.com")).isNotNull();
        assertThat(emailRepository.findByEmail("test@test.com")).isNotNull();
    }

    @Test
    public void 비정상_회원가입() {
        // given
        SignupReq test = SignupReq.builder()
                .email("test@test.com")
                .password("4567")
                .name("Test")
                .build();

        Mockito.when(loginServiceMock.signup(test))
                .thenThrow(IllegalArgumentException.class);

        assertThat(emailRepository.findByEmail("test@test.com")).isEmpty();

    }

}