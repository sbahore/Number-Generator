
package com.vmware.numbergenerator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Error Handling Controller
 */
@ControllerAdvice
@RestController
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder builder = new StringBuilder();
		ex.getBindingResult()
		        .getAllErrors()
		        .forEach((error) -> {
			        StringBuilder errorMessage = new StringBuilder();
			        errorMessage.append(error.getDefaultMessage())
			                .append(" : ")
			                .append(((FieldError) error).getRejectedValue())
			                .append("\n");
			        builder.append(errorMessage);
		        });
		return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity
	       handleViolationExceptions(ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> violations
		    = constraintViolationException.getConstraintViolations();
		String errorMessage;
		if (!violations.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			violations.forEach(violation -> builder.append(" ")
			        .append(violation.getMessage())
			        .append(" : ")
			        .append(violation.getInvalidValue())
			        .append("\n"));
			errorMessage = builder.toString();
		} else {
			errorMessage = "ConstraintViolationException";
		}
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
