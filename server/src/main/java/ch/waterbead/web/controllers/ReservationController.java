package ch.waterbead.web.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.waterbead.models.Event;
import ch.waterbead.models.EventUtil;
import ch.waterbead.models.Reservation;
import ch.waterbead.repositories.ReservationRepository;
import ch.waterbead.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired ReservationService reservationService;
	@Autowired ReservationRepository reservationRepository;
	
	@RequestMapping("/feed")
	public List<Event> byMonth(@RequestParam("start") String start, @RequestParam("end") String end) {
		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);
		List<Reservation> reservations = reservationRepository.findByMonthAndYear(startDate, endDate);
		return EventUtil.toEvents(reservations);
	}
	
	@RequestMapping(consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public void add(@RequestBody Reservation reservation) {
		if(isReservationsNotExist(reservation.getFrom(), reservation.getTo()))
			reservationService.add(reservation);
	}
	
	@RequestMapping(consumes="application/json",produces="application/json",method=RequestMethod.PUT)
	public void update(@RequestBody Reservation reservation) {
		reservationService.update(reservation);
	}
	
	@RequestMapping(consumes="application/json",produces="application/json",method=RequestMethod.DELETE)
	public void delete(@RequestBody Reservation reservation) {
		reservationService.delete(reservation);
	}
	
	private boolean isReservationsNotExist(LocalDate from, LocalDate to) {
		return reservationRepository.findByMonthAndYear(from, to).size() == 0;
	}
}
