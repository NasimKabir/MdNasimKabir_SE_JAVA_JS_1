package com.spring.service;

import javax.servlet.http.HttpServletRequest;

import com.spring.dto.LoginRequestDto;
import com.spring.dto.UserDto;
import com.spring.view.Response;



public interface AuthenticationService {
	public Response login( LoginRequestDto loginRequest, HttpServletRequest request);
	public Response registerUser(UserDto userDto);
}
