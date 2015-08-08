package ch.waterbead.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("L'utilisateur " + username + " n'existe pas");
		}
		return user;
	}

}
