package com.alvaro.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	  @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public FieldError[] validationError(MethodArgumentNotValidException ex) {
	        BindingResult result = ex.getBindingResult();
	        final List<FieldError> fieldErrors = result.getFieldErrors();
	        return fieldErrors.toArray(new FieldError[0]);
	    }

	    @ExceptionHandler(ConstraintViolationException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public String handleConstraintViolationException(ConstraintViolationException e) {
	        return "not valid due to validation error: " + e.getMessage();
	    }
}
