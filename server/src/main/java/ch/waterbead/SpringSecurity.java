package ch.waterbead;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;
import ch.waterbead.security.RestAuthenticationEntryPoint;
import ch.waterbead.security.SecurityFilter;

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
	
	
	@Bean
	public SecurityFilter securityFilter() {
		return new SecurityFilter();
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
		http.authorizeRequests().anyRequest().permitAll();
		http.addFilterAfter(securityFilter(),SecurityContextPersistenceFilter.class).authorizeRequests().antMatchers(HttpMethod.POST, "/*").authenticated()
			.and().authorizeRequests().antMatchers(HttpMethod.PUT, "/*").authenticated()
			.and().authorizeRequests().antMatchers(HttpMethod.HEAD, "/*").authenticated();
		http.csrf().disable();
		http.rememberMe();
		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
	}
}