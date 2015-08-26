package ch.waterbead.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.waterbead.converters.JsonDateDeserializer;
import ch.waterbead.converters.JsonDateSerializer;

@Embeddable
public class ReservationPeriod {
	@Column(name="DATE_DEBUT",columnDefinition="DATE")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDate from;
	@Column(name="DATE_FIN",columnDefinition="DATE")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)	
	private LocalDate to;
	
	public ReservationPeriod() {
		
	}
	
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
		return (from.isBefore(to) || from.isEqual(to)) && Period.between(from, to).getDays() > 0;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}
}
