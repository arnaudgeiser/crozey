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
import ch.waterbead.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired ReservationService reservationService;
	
	Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now());
	
	@RequestMapping("/bymonth")
	public List<Reservation> byMonth(@RequestParam(value="month") int month, @RequestParam(value="year") int year) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping("/byyear")
	public List<Reservation> byYear(@RequestParam(value="year") int year) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping(name="add", method=RequestMethod.POST)
	public void add(Reservation reservation) {
		reservationService.add(reservation);
	}
		
}
