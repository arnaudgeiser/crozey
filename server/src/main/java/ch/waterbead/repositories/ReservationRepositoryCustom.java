package ch.waterbead.repositories;

import java.time.LocalDate;
import java.util.List;

import ch.waterbead.models.Reservation;

public interface ReservationRepositoryCustom {
	List<Reservation> findByMonthAndYear(LocalDate start, LocalDate end);
	List<Reservation> findByYear(int year);
}
