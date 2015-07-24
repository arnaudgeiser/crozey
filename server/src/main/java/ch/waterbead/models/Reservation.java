package ch.waterbead.models;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Embedded
	private ReservationPeriod period;
	@ManyToOne
	private User user;
	private boolean privacy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReservationPeriod getPeriod() {
		return period;
	}
	public void setPeriod(ReservationPeriod period) {
		this.period = period;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getFrom() {
		return period.getFrom();
	}
	public LocalDate getTo() {
		return period.getTo();
	}
	public boolean isPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	
	public boolean hasSameId(Reservation reservation) {
		return reservation.getId().equals(id);
	}
}