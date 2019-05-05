package com.heroes.gijoe.configuration;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.heroes.gijoe.error.ClientDNMissingAuthenticationException;

public class X509AuthAuthenticationProvider implements AuthenticationProvider {
	
	public X509AuthAuthenticationProvider() {
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("x509auth provider");
		String name = authentication.getName();
        X509Certificate credentials = (X509Certificate)authentication.getCredentials();
//        throw new ClientDNMissingAuthenticationException("Missing Client DN");
        return new PreAuthenticatedAuthenticationToken(credentials.getSubjectDN(), credentials);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
	
    private static class User {
        private String name;
        private String password;
        private String role;

        public User(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }

        public boolean match(String name, String password) {
            return this.name.equals(name) && this.password.equals(password);
        }
    }

}
