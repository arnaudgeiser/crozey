package ch.waterbead;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;
import ch.waterbead.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@PostConstruct
	public void tets() {
		User user = new User();
		user.setUsername("arnaud");
		user.setPassword("pass");
		userRepository.save(user);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.rememberMe();
		http.authorizeRequests().antMatchers("/reservations/feed").permitAll();
		http.authorizeRequests().antMatchers("/authentication/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		//http.sessionManagement().maximumSessions(1);
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
	}
}