package ch.waterbead.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.context.annotation.Configuration;

public class ReservationPeriod {
	private final LocalDate from;
	private final LocalDate to;
	
	public ReservationPeriod(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}

	public LocalDate getFrom() {
		return from;
	}
	public LocalDate getTo() {
		return to;
	}
	
	public boolean chevauche(List<ReservationPeriod> reservations) {
		boolean chevauche = false;
		for(ReservationPeriod r : reservations) {
			if((r.from.isAfter(from) || r.from.isEqual(from)) && r.to.isBefore(to)) chevauche= true;
		}
		return chevauche;
	}

	public boolean isValid() {
		return Period.between(from, to).getDays() > 0;
	}
}
