package ch.waterbead.web.controllers;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController()
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(@RequestBody ObjectNode node, HttpServletRequest request) {
		request.getSession(true);
		String username = node.get("username").asText();
		String password = node.get("password").asText();

		Authentication requete = new UsernamePasswordAuthenticationToken(username, password);
		try {
			Authentication result = authenticationManager.authenticate(requete);
			SecurityContextHolder.getContext().setAuthentication(result);
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		}
		catch(AuthenticationException e) {
			e.printStackTrace();
		}		
	}
}
