package ch.waterbead;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ch.waterbead.models.User;
import ch.waterbead.repositories.UserRepository;
import ch.waterbead.security.CustomUserDetailsService;
import ch.waterbead.security.SecurityFilter;
import ch.waterbead.services.ReservationService;
import ch.waterbead.web.filters.RESTEntryPoint;

@Configuration
@EnableJpaRepositories(basePackages = "ch.waterbead.repositories")
@EnableTransactionManagement
public class SpringConfiguration {
	private static final ReservationService reservationService = new ReservationService();

	@Bean
	public ReservationService getReservationService() {
		return reservationService;
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
		
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("ch.waterbead.models");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
	@Bean
	public UserDetailsService UserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@EnableWebSecurity
	public static class SpringSecurity extends WebSecurityConfigurerAdapter {
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
			http.authorizeRequests().antMatchers(HttpMethod.POST, "/*").authenticated()
				.and().authorizeRequests().antMatchers(HttpMethod.PUT, "/*").authenticated()
				.and().authorizeRequests().antMatchers(HttpMethod.HEAD, "/*").authenticated();
			http.authorizeRequests().antMatchers("/reservations/feed").permitAll();
			http.csrf().disable();
			http.rememberMe();
		}
	}
}

