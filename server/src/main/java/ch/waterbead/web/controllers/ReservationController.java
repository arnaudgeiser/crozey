package ch.waterbead.web.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.waterbead.models.Reservation;
import ch.waterbead.models.ReservationPeriod;
import ch.waterbead.repositories.ReservationRepository;
import ch.waterbead.services.ReservationService;
import ch.waterbead.web.dto.RequestReservationByMonth;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired ReservationService reservationService;
	@Autowired ReservationRepository reservationRepository;
	
	Reservation reservation = new Reservation();
	
	@RequestMapping("/display")
	public List<Reservation> display() {
		return reservationRepository.findAll();
	}
	
	@RequestMapping("/bymonth")
	public List<Reservation> byMonth(@RequestParam(value="month")int month, @RequestParam(value="year") int year) {
		return reservationRepository.findByMonthAndYear(month, year);
	}
	
	@RequestMapping("/byyear")
	public List<Reservation> byYear(@RequestParam(value="request") RequestReservationByMonth request) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping(name="/add", method=RequestMethod.POST)
	public void add(Reservation reservation) {
		reservationService.add(reservation);
	}
	
	@RequestMapping(name="/addone")
	public void addRandom() {
		Reservation reservation = new Reservation();
		reservation.setPeriod(new ReservationPeriod(LocalDate.now(), LocalDate.now().plusDays(10)));
		reservationService.add(reservation);
	}
	
	@RequestMapping(name="/update", method=RequestMethod.PUT)
	public void update(Reservation reservation) {
		reservationService.update(reservation);
	}
	
	@RequestMapping(name="/delete",method=RequestMethod.DELETE)
	public void delete(Reservation reservation) {
		reservationService.delete(reservation);
	}
}
