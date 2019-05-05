package com.heroes.gijoe.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;
import com.heroes.gijoe.error.ClientDNMissingAuthenticationException;
import com.heroes.gijoe.error.ErrorHandlerMessage;

//@ControllerAdvice
public class BWSExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
	  
      // MissingServletRequestParameterException handling code goes here.
	  ErrorHandlerMessage error = new ErrorHandlerMessage();
		error.setError("Missing param");
		error.setErrorMessage("param is missing");

		Gson gson = new Gson();
		ResponseEntity<Object> response = new ResponseEntity(ErrorHandlerMessage.class, status);

		 return ResponseEntity
	            .status(HttpStatus.FORBIDDEN)
	            .body(gson.toJson(error));
  }

//  @ExceptionHandler(value = { Exception.class } )
//  public ResponseEntity<Object> handleOtherExceptions(final Exception ex, 
//    final WebRequest req) {
//
//  }

}