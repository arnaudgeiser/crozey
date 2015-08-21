package ch.waterbead.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.waterbead.models.Reservation;
import ch.waterbead.models.User;
import ch.waterbead.repositories.ReservationRepository;

@Service
public class ReservationService {
	@Autowired
	ReservationRepository reservationRepository;
	
	public void add(Reservation reservation) {
		User user = getCurrentUser();
		reservation.setUser(user);
		if(isReservationsNotExist(reservation)) {
			reservationRepository.saveAndFlush(reservation);
		}
	}
	
	public void update(Reservation reservation) {
		User user = getCurrentUser();
		reservation.setUser(user);
		if(hasRightToModify(reservation) && !isReservationsNotExistForUpdate(reservation)) {
			reservationRepository.saveAndFlush(reservation);
		}
	}
	
	public void deleteById(Long id) {
		Reservation reservation = reservationRepository.findOne(id);
		User user = getCurrentUser();
		reservation.setUser(user);
		if(hasRightToModify(reservation)) {
			reservationRepository.delete(reservation);
		}
	}
	
	private boolean isReservationsNotExist(Reservation reservation) {
		return reservationRepository.findReservationsChevauchantes(reservation).size() == 0;
	}
	
	private boolean isReservationsNotExistForUpdate(Reservation currentReservation) {
		List<Reservation> reservations = reservationRepository.findReservationsChevauchantes(currentReservation);
		if(reservations.size() == 0) return false;
		if(reservations.size() > 1) return true;
		if(reservations.get(0).equals(currentReservation)) return false;
		return true;
	}
	
	private boolean hasRightToModify(Reservation reservation) {
		User user = getCurrentUser();
		Reservation reservationExistante = reservationRepository.findOne(reservation.getId());
		if(reservationExistante.getUser().equals(user)) {
			return true;
		}
		return false;
	}
	
	private User getCurrentUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
