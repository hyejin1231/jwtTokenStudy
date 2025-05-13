package com.example.jwtoken.service;

import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.req.SignupReq;
import com.example.jwtoken.dto.res.JwtTokenRes;

public interface LoginService
{
	JwtTokenRes getUserInfoBy(LoginReq loginReq);

	JwtTokenRes signup(SignupReq signupReq);

}
