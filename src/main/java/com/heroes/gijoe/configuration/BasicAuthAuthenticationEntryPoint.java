package com.heroes.gijoe.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.heroes.gijoe.error.ClientDNMissingAuthenticationException;
import com.heroes.gijoe.error.ErrorHandlerMessage;

@Component
public class BasicAuthAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ErrorHandlerMessage error = new ErrorHandlerMessage();
		error.setError("Bad Password");
		error.setErrorMessage(authException.getMessage());
		if(authException instanceof ClientDNMissingAuthenticationException) {
			error.setError("Client DN missing");
		}
		Gson gson = new Gson();
		response.getOutputStream().println(gson.toJson(error));

	}

}
