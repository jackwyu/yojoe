package com.heroes.gijoe.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;

public class RestExceptionTranslationFilter extends ExceptionTranslationFilter {
	
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	
    @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
//		super.doFilter(req, res, chain);
    	HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;


		try {
			if(request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {
				System.out.println(request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
				UsernameNotFoundException ue = (UsernameNotFoundException) request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
				BasicAuthAuthenticationEntryPoint ep = new BasicAuthAuthenticationEntryPoint();
				ep.commence(request, response, ue);
			} else {
			
				chain.doFilter(request, response);
	
				logger.debug("Chain processed normally");
			}
//		}catch (IOException ex) {
//			throw ex;
		} catch (Exception ex) {
			// Try to extract a SpringSecurityException from the stacktrace
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer
					.getFirstThrowableOfType(AuthenticationException.class, causeChain);
			System.out.println("inside rest transalation filter : " + ase.getClass().getCanonicalName());
			if (ase instanceof AuthenticationException) {
				logger.debug(
						"Authentication exception occurred; redirecting to authentication entry point",
						ase);

//				sendStartAuthentication(request, response, chain,
//						(AuthenticationException) ase);
				System.out.println("AuthenticationException");
			}
			else if (ase instanceof AccessDeniedException) {
				System.out.println("AccessDeniedException");
			}
		}
		
		
	}
    
    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		/**
		 * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
		 */
		protected void initExtractorMap() {
			super.initExtractorMap();

			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable,
							ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}

	}
    
	@Override
	protected void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			AuthenticationException reason) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	System.out.println(reason.getClass().getCanonicalName());
		super.sendStartAuthentication(request, response, chain, reason);
	}

	public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache) {
        super(authenticationEntryPoint, requestCache);
    }
}