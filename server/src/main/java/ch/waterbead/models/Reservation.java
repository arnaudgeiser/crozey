package ch.waterbead.models;

public class Reservation {
	private ReservationPeriod period;
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
