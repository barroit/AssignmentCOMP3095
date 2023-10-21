package com.gbc.springsocial.service.user.advisor;

import com.gbc.springsocial.shared.exception.NotFoundException;
import com.gbc.springsocial.shared.exception.MissingFieldException;
import com.gbc.springsocial.shared.exception.UniversalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({ NotFoundException.class, MissingFieldException.class })
	public ResponseEntity<String> handleValidateException(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler({ UniversalException.class })
	public ResponseEntity<String> handleBasicException(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
