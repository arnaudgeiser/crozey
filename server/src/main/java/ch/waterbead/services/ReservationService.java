package ch.waterbead.services;

import org.springframework.beans.factory.annotation.Autowired;

import ch.waterbead.models.Reservation;
import ch.waterbead.repositories.ReservationRepository;

public class ReservationService {
	@Autowired
	ReservationRepository reservationRepository;
	
	public void add(Reservation reservation) {
		reservationRepository.saveAndFlush(reservation);
	}
	
	public void update(Reservation reservation) {
		reservationRepository.saveAndFlush(reservation);
	}
	
	public void delete(Reservation reservation) {
		reservationRepository.delete(reservation);
	}
}
