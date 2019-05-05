package com.heroes.gijoe.configuration;

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.heroes.gijoe.error.ClientDNMissingAuthenticationException;

//@Component
public class CustomX509Filter extends GenericFilterBean {
//	@Autowired
//	private X509PrincipalExtractor principalExtractor = new SubjectDnX509PrincipalExtractor();
//	@Autowired
//	private BasicAuthAuthenticationEntryPoint entryPoint;
//	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		BasicAuthAuthenticationEntryPoint entryPoint = new BasicAuthAuthenticationEntryPoint();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse= (HttpServletResponse) response;
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		X509Certificate x509Cert = (X509Certificate)authentication.getCredentials();
		X509Certificate x509Cert = extractClientCertificate(httpServletRequest);
		System.out.println("Custom filter: user from token: " + x509Cert.getSubjectDN().getName());
		
		try {
			Collection<List<?>> sanList = x509Cert.getSubjectAlternativeNames();
			System.out.println(sanList);
//			sanList.stream().collect(Collectors.groupingBy())
		} catch (CertificateParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		entryPoint.commence(httpServletRequest, httpServletResponse, new ClientDNMissingAuthenticationException("Missing client DN"));
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		X509Certificate cert = extractClientCertificate(request);
		if (cert == null) {
			return null;
		}
		return cert.getSubjectDN();
	}
	private X509Certificate extractClientCertificate(HttpServletRequest request) {
		X509Certificate[] certs = (X509Certificate[]) request
				.getAttribute("javax.servlet.request.X509Certificate");
		if (certs != null && certs.length > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("X.509 client authentication certificate:" + certs[0]);
			}
			return certs[0];
		}
		if (logger.isDebugEnabled()) {
			logger.debug("No client certificate found in request.");
		}
		return null;
	}
}
