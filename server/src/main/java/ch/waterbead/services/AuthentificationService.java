package ch.waterbead.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;

@Service
public class AuthentificationService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	
	public AuthenticationObject logging(String username, String password, HttpSession session) {
		Authentication requete = new UsernamePasswordAuthenticationToken(username, password);
		try {
			Authentication result = authenticationManager.authenticate(requete);
			SecurityContextHolder.getContext().setAuthentication(result);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		}
		catch(AuthenticationException e) {
			return AuthenticationObject.NOT_AUTHENTICATED;
		}
		User user = userRepository.findByUsername(username);
		return new AuthenticationObject(user.getFirstNameLastName());
	}
}
