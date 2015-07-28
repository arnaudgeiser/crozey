package ch.waterbead.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.waterbead.models.Reservation;
import ch.waterbead.repositories.ReservationRepository;

@Service
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
