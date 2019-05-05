package com.heroes.gijoe.error;

import org.springframework.security.core.AuthenticationException;

public class ClientDNMissingAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -58997170367184605L;

	public ClientDNMissingAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
