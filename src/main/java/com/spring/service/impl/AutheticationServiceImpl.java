package com.spring.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.LoginRequestDto;
import com.spring.dto.LoginResponseDto;
import com.spring.dto.UserDto;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;
import com.spring.jwt.JwtTokenProvider;
import com.spring.model.ERole;
import com.spring.model.Role;
import com.spring.model.User;
import com.spring.repository.RoleRepository;
import com.spring.repository.UserRepository;
import com.spring.security.UserDetailsImpl;
import com.spring.service.AuthenticationService;

@Service
public class AutheticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Autowired
	public AutheticationServiceImpl( AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public Response login(LoginRequestDto loginRequest,HttpServletRequest request) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		if (authentication.isAuthenticated()) {
			LoginResponseDto loginResponse = new LoginResponseDto();
			loginResponse.setToken(jwt);
			loginResponse.setId(userDetails.getId());
			loginResponse.setUsername(userDetails.getUsername());
			loginResponse.setEmail(userDetails.getEmail());
			loginResponse.setRoles(roles);
			return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Logged In Success", loginResponse);
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Username or password");
	}
	
	
	
	@Override
	public Response registerUser(UserDto signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
		}
		// Create new user's account
		User user=modelMapper.map(signUpRequest, User.class);
		if(user !=null) {
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> role = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(userRole);
		} else {

			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
			role.add(adminRole);
		}

		user.setRoles(role);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		userRepository.save(user);

		return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "User registered successfully!");
		}
  		return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Bad request.");


	}
}
