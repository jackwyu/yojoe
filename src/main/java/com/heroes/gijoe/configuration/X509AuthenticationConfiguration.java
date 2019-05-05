package com.heroes.gijoe.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import com.heroes.gijoe.service.X509UserDetailsService;

@Configuration
@EnableWebSecurity
public class X509AuthenticationConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private X509UserDetailsService userDetailsService;
	@Autowired
	private BasicAuthAuthenticationEntryPoint basicAuthAuthenticationEntryPoint;
//	@Autowired
//	private CustomX509Filter x509Filter;
	@Autowired
	private X509AccessDeniedHandler x509AccessDeniedHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.x509()
		.authenticationUserDetailsService(userDetailsService)
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(new BasicAuthAuthenticationEntryPoint())
		.and()
		.addFilterBefore(new CustomX509Filter(), X509AuthenticationFilter.class)
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("localhost").password("none").roles("USER");
	}
	
	@Bean
	public X509AccessDeniedHandler accessDeniedHandler(){
	    return new X509AccessDeniedHandler();
	}
}
