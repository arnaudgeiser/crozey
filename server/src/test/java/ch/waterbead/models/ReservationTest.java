package ch.waterbead.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReservationTest {
	private ReservationPeriod reservation;
	private List<ReservationPeriod> reservations;
	
	@Before
	public void setUp() {
		reservation = createReservation("2015-01-01", "2015-01-10");
		reservations = new ArrayList<>();
	}
	
	@Test
	public void chevauche_GivenValidPeriod_ShouldBeFalse() {
		Assert.assertEquals(reservation.chevauche(reservations), false);
	}
	
	@Test
	public void chevauche_GivenReservationChevauchante1_ShouldBeTrue() {
		reservations.add(createReservation("2015-01-01", "2015-01-03"));
		Assert.assertEquals(reservation.chevauche(reservations), true);
	}
	
	@Test
	public void chevauche_GivenReservationChevauchante2_ShouldBeTrue() {
		reservations.add(createReservation("2015-01-01", "2015-01-01"));
		Assert.assertEquals(reservation.chevauche(reservations), true);
	}
	
	@Test
	public void chevauche_GivenReservationNonChevauchante_ShouldBeFalse() {
		reservations.add(createReservation("2015-01-10", "2015-01-11"));
		Assert.assertEquals(reservation.chevauche(reservations), false);
	}
	
	@Test
	public void chevauche_GivenReservationNonChevauchante2_ShouldBeTrue() {
		reservations.add(createReservation("2014-12-31", "2015-01-01"));
		Assert.assertEquals(reservation.chevauche(reservations), false);
	}
	
	@Test
	public void isValid_GivenReservationOfOneDay_ShouldBeFalse() {
		reservation = createReservation("2015-01-01", "2015-01-01");
		Assert.assertEquals(reservation.isValid(), false);
	}
	
	@Test
	public void isValid_GivenReservationOfTwoDays_ShouldBeFalse() {
		reservation = createReservation("2015-01-01", "2015-01-02");
		Assert.assertEquals(reservation.isValid(), true);
	}
	
	private ReservationPeriod createReservation(String sFrom, String sTo) {
		LocalDate from = createDate(sFrom);
		LocalDate to = createDate(sTo);
		return  new ReservationPeriod(from, to);
	}
	
	private LocalDate createDate(String date) {
		return LocalDate.parse(date);
	}
}
