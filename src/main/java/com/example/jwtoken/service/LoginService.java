package com.example.jwtoken.service;

import com.example.jwtoken.dto.req.LoginReq;
import com.example.jwtoken.dto.res.JwtTokenRes;
import com.example.jwtoken.dto.res.LoginRes;

public interface LoginService
{
	JwtTokenRes getUserInfoBy(LoginReq loginReq);
}
