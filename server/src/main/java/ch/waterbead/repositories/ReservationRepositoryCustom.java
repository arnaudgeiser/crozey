package ch.waterbead.repositories;

import java.util.List;

import ch.waterbead.models.Reservation;

public interface ReservationRepositoryCustom {
	List<Reservation> findByMonthAndYear(int month, int year);
	List<Reservation> findByYear(int year);
}
