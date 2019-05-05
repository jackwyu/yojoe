package com.heroes.gijoe.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.heroes.gijoe.service.X509UserDetailsService;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private X509UserDetailsService userDetailsService;
	
	@Autowired
	private BasicAuthAccessDeniedHandler basicAuthAccessDeniedHandler;
	
	@Autowired
	private BasicAuthAuthenticationEntryPoint basicAuthAuthenticationEntryPoint;
	
//	public void configure(AuthenticationManagerBuilder builder) throws Exception{
//		builder.authenticationProvider(new BasicAuthAuthenticationProvider());
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/**")
		.authenticated()
		.and()
		.httpBasic()
		.and()
//		.userDetailsService(userDetailsService)
		.exceptionHandling()
		.authenticationEntryPoint(basicAuthAuthenticationEntryPoint).and().authenticationProvider(new BasicAuthAuthenticationProvider());
	}
	
	@Bean 
	public AuthenticationFailureHandler basicAuthenticationFailureHandler() {
		return new BasicAuthenticationFailureHandler();
	}
}
