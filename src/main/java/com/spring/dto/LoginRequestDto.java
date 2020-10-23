package com.spring.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "username field should not be empty")
    @Size(min = 4,max = 20,message = "length should be in between 4 to 20")
    private String username;
    @NotBlank(message = "Password Mandatory")
    @Size(min = 4, max = 20, message = "length should be in between 4 to 20")
    private String password;
}