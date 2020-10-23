package com.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.dto.LoginRequestDto;
import com.spring.dto.UserDto;
import com.spring.service.AuthenticationService;
import com.spring.view.Response;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	  @PostMapping("/login")
	    public Response login(@Valid @RequestBody LoginRequestDto login, HttpServletRequest request, HttpServletResponse response){
	        return authenticationService.login(login, request);
	    }
	  
	  @PostMapping("/signup")
	    public Response Register(@Valid @RequestBody UserDto signUpRequest, HttpServletRequest request, HttpServletResponse response){
	        return authenticationService.registerUser(signUpRequest);
	    }
}
