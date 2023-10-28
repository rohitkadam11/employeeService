package com.example.rqchallenge.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rqchallenge.exception.EmployeeCustomException;
import com.example.rqchallenge.exception.EmployeeCustomExceptionModel;

@RestControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(value = EmployeeCustomException.class)
	public ResponseEntity<Object> exception(EmployeeCustomException exception) {
		EmployeeCustomExceptionModel exceCustomException = new EmployeeCustomExceptionModel(exception.getMessage(),
				exception.getErrorCode(), exception.getErrorDescrption());
		return new ResponseEntity<>(exceCustomException, HttpStatus.NOT_FOUND);
	}
}
