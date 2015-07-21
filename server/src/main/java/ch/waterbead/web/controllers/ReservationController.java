package ch.waterbead.web.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.waterbead.models.Event;
import ch.waterbead.models.EventUtil;
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
	
	@RequestMapping("/feed")
	public List<Event> byMonth(@RequestParam("start") String start, @RequestParam("end") String end) {
		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);
		List<Reservation> reservations = reservationRepository.findByMonthAndYear(startDate, endDate);
		return EventUtil.toEvents(reservations);
	}
	
	@RequestMapping("/byyear")
	public List<Reservation> byYear(@RequestParam(value="request") RequestReservationByMonth request) {
		return Arrays.asList(reservation);
	}
	
	@RequestMapping(value="/add",consumes="application/json",produces="application/json")
	public void add(@RequestBody Reservation reservation) {
		reservationService.add(reservation);
	}
	
	@RequestMapping(name="/addone")
	public void addRandom() {
		Random random = new Random();
		int month = random.nextInt(12)+1;
		Reservation reservation = new Reservation();
		reservation.setPeriod(new ReservationPeriod(LocalDate.now().withMonth(month), LocalDate.now().withMonth(month).plusDays(10)));
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
