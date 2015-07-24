package ch.waterbead.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
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
		//UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		
		Authentication request = new UsernamePasswordAuthenticationToken("arnaud", "pass");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) {
			boolean isAuthentificated = auth.isAuthenticated();
		}
		try {
			Authentication result = getAuthenticationManager().authenticate(request);
			SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
			SecurityContextHolder.getContext().setAuthentication(result);
		}
		catch(AuthenticationException e) {
			e.printStackTrace();
		}
		chain.doFilter(req, res);
	}
}
