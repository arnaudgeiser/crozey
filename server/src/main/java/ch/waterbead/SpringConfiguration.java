package ch.waterbead;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.waterbead.services.ReservationService;

@Configuration
public class SpringConfiguration {
	private static final ReservationService reservationService = new ReservationService();
	
	@Bean
	public ReservationService getReservationService() {
		return reservationService;
	}
}
