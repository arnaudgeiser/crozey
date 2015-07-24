package ch.waterbead.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username==null || password ==null) return;
		
		Authentication request = new UsernamePasswordAuthenticationToken(username, password);
		try {
			Authentication result = getAuthenticationManager().authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
		}
		catch(AuthenticationException e) {
			logger.error(e);
		}
		chain.doFilter(req, res);
	}
}
