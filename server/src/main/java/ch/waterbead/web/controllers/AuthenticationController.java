package ch.waterbead.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;
import ch.waterbead.services.AuthentificationService;
import ch.waterbead.util.Response;

@RestController()
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthentificationService authentificationService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Response login(@RequestBody ObjectNode node, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = node.get("username").asText();
		String password = node.get("password").asText();
		
		if(!authentificationService.logging(username, password, request.getSession(true))) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Je crois pas, non !");
		}
		return Response.ok();
	}
	
	@RequestMapping(value="/logout")
	public Response logout(HttpServletRequest request) throws ServletException {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.ok();
	}
	
	@RequestMapping(value="/logged",produces="application/json")
	public boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) return false;
		SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if(context==null) return false;
		Authentication auth = context.getAuthentication();
		if(auth == null) return false;
		return true;
	}
	
	@RequestMapping(value="/newaccount", method=RequestMethod.POST) 
	public Response createAccount(@RequestBody User user, HttpServletRequest request) {
		userRepository.save(user);
		authentificationService.logging(user.getUsername(), user.getPassword(), request.getSession(true));
		return Response.ok();
	}
}
