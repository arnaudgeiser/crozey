package ch.waterbead.web.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.waterbead.models.ReservationPeriod;
import ch.waterbead.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired ReservationService reservationService;
	
	ReservationPeriod reservation = new ReservationPeriod(LocalDate.now(), LocalDate.now());
	
	@RequestMapping("/bymonth")
	public List<ReservationPeriod> byMonth(@RequestParam(value="month") int month, @RequestParam(value="year") int year) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping("/byyear")
	public List<ReservationPeriod> byYear(@RequestParam(value="year") int year) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping(name="/add", method=RequestMethod.POST)
	public void add(ReservationPeriod reservation) {
		reservationService.add(reservation);
	}
	
	@RequestMapping(name="/update", method=RequestMethod.PUT)
	public void update(ReservationPeriod reservation) {
		reservationService.update(reservation);
	}
	
	@RequestMapping(name="/delete",method=RequestMethod.DELETE)
	public void delete(ReservationPeriod reservation) {
		reservationService.delete(reservation);
	}
}
