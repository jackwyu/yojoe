package com.heroes.gijoe.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.heroes.gijoe.error.ErrorHandlerMessage;
@Component
public class BasicAuthAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ErrorHandlerMessage error = new ErrorHandlerMessage();
		error.setError("Bad Password");
		error.setErrorMessage("The password you used was badk");
		
		Gson gson = new Gson();
		response.getOutputStream().println(gson.toJson(error));
	}

}
