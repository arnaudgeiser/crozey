package ch.waterbead.repositories;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import ch.waterbead.models.Reservation;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
	@Autowired
    private EntityManager em;
	
	private static final String QUERY_RESERVATIONS = "SELECT r FROM Reservation r WHERE r.period.from > :startDate AND r.period.to < :endDate";
	
	private static final String PARAM_START_DATE = "startDate";
	private static final String PARAM_END_DATE = "endDate";

	@Override
	public List<Reservation> findByMonthAndYear(int month, int year) {
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.with(TemporalAdjusters.lastDayOfMonth());
		return getReservations(start, end);

	}

	@Override
	public List<Reservation> findByYear(int year) {
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = start.with(TemporalAdjusters.lastDayOfYear());
		return getReservations(start, end);
	}
	
	@SuppressWarnings("unchecked")
	private List<Reservation> getReservations (LocalDate start, LocalDate end) {
		return em.createQuery(QUERY_RESERVATIONS)
				.setParameter(PARAM_START_DATE, start)
				.setParameter(PARAM_END_DATE, end)
				.getResultList();
	}
}
