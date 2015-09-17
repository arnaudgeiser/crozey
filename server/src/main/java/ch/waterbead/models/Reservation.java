package ch.waterbead.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RESERVATIONS")
public class Reservation {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	@Column(name="TITRE")
	private String title;
	@Embedded
	private ReservationPeriod period;
	@ManyToOne
	private User user;
	@Column(name="PRIVE")
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}