package ch.waterbead.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.hamcrest.core.IsEqual;


public class Reservation {
	private final LocalDate from;
	private final LocalDate to;
	
	public Reservation(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}

	public LocalDate getFrom() {
		return from;
	}
	public LocalDate getTo() {
		return to;
	}
	
	public boolean chevauche(List<Reservation> reservations) {
		boolean chevauche = false;
		for(Reservation r : reservations) {
			if((r.from.isAfter(from) || r.from.isEqual(from)) && r.to.isBefore(to)) chevauche= true;
		}
		return chevauche;
	}

	public boolean isValid() {
		return Period.between(from, to).getDays() > 0;
	}
}
