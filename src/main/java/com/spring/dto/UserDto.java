package com.spring.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	@Size(min = 4, max = 20,message = "User Name is Mandatory")
	private String username;

	//@NotBlank(message = "You Have To Enter The Password")
	@Size(min = 4, max = 20, message = "Password length should be in between 8 to 20")
	private String password;

	@NotBlank(message = "Email field should not be empty")
	@Email( message = "Invalid Email Pattern")
	private String email;

	//@NotEmpty(message = "Phone Number is Mandatory")
	@Pattern(regexp = "^(?:\\+?88)?01[3-9]\\d{8}$", message = "invalid mobile number.")
	@Size(max = 11, message = "digits should be 11")
	private String phone;

	public String getPassword() {
		return password;
	}

	private Set<String> role;

}