package ch.waterbead.models;

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
	@Embedded
	private ReservationPeriod period;
	@ManyToOne
	private User user;
	
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
}
