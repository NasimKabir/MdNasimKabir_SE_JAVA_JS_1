package com.spring.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception) {
		// 1. create payload contain exception details
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		ApiException apiException = new ApiException(exception.getMessage(), exception, badRequest,
				ZonedDateTime.now(ZoneId.of("Z")));
		// 2. return response entity
		return new ResponseEntity<>(apiException, badRequest);
	}
}