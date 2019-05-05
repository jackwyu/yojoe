package com.heroes.gijoe.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;
import com.heroes.gijoe.error.ErrorHandlerMessage;


public class BasicAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ErrorHandlerMessage error = new ErrorHandlerMessage();
		error.setError("Bad Password");
		error.setErrorMessage("The password you used was badk");
		
		Gson gson = new Gson();
		response.getOutputStream().println(gson.toJson(error));

	}

}
