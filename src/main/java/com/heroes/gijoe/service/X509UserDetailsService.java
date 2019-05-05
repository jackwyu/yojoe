package com.heroes.gijoe.service;

import java.security.cert.X509Certificate;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.heroes.gijoe.error.ClientDNMissingAuthenticationException;

@Service
public class X509UserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("LoadUserByUserName : " + username);
//		List<GrantedAuthority> GITHUB_FREE_AUTHORITIES
//	     = AuthorityUtils.commaSeparatedStringToAuthorityList(
//	     "GITHUB_USER,GITHUB_USER_FREE");
//	    List<GrantedAuthority> GITHUB_SUBSCRIBED_AUTHORITIES 
//	     = AuthorityUtils.commaSeparatedStringToAuthorityList(
//	     "GITHUB_USER,GITHUB_USER_SUBSCRIBED");
//		User user = new User(username, username, GITHUB_FREE_AUTHORITIES);
//		return user;
//	}

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("loadUserDetails by token");
		List<GrantedAuthority> USER_AUTHORITIES 
	     = AuthorityUtils.commaSeparatedStringToAuthorityList(
	     "USER");
		X509Certificate cert = (X509Certificate)token.getCredentials();
		User user = new User(cert.getSubjectDN().getName(), "", USER_AUTHORITIES);
		throw new ClientDNMissingAuthenticationException("error");
//		return user;
	}

}
