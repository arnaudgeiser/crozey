package ch.waterbead.web.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.waterbead.models.Event;
import ch.waterbead.models.EventUtil;
import ch.waterbead.models.Reservation;
import ch.waterbead.models.User;
import ch.waterbead.repositories.ReservationRepository;
import ch.waterbead.services.ReservationService;
import ch.waterbead.util.Response;
import sun.util.resources.cldr.ne.CurrencyNames_ne;

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
	public Reservation add(@RequestBody Reservation reservation, HttpServletRequest req) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		reservation.setUser(user);
		if(isReservationsNotExist(reservation.getFrom(), reservation.getTo()))
			reservationService.add(reservation);
		return reservation;
	}
	
	@RequestMapping(value="/{id}",consumes="application/json",produces="application/json",method=RequestMethod.PUT)
	public Response update(@RequestBody Reservation reservation) {
		User user = getCurrentUser();
		reservation.setUser(user);
		if(hasRightToModify(reservation) && !isReservationsNotExist(reservation.getFrom(), reservation.getTo(), reservation)) {
			reservationService.update(reservation);
		}
		return Response.ok();
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Response delete(@PathVariable(value="id") long id) {
		Reservation reservation = reservationRepository.findOne(id);
		User user = getCurrentUser();
		reservation.setUser(user);
		if(hasRightToModify(reservation)) {
			reservationService.delete(reservation);
		}
		return Response.ok();
	}
	
	private boolean isReservationsNotExist(LocalDate from, LocalDate to) {
		return reservationRepository.findByMonthAndYear(from, to).size() == 0;
	}
	
	private boolean isReservationsNotExist(LocalDate from, LocalDate to,Reservation currentReservation) {
		List<Reservation> reservations = reservationRepository.findByMonthAndYear(from, to);
		if(reservations.size() == 0) return false;
		if(reservations.size() > 1) return true;
		if(reservations.get(0).equals(currentReservation)) return false;
		return true;
		
	}
	
	private boolean hasRightToModify(Reservation reservation) {
		User user = getCurrentUser();
		Reservation reservationExistante = reservationRepository.findOne(reservation.getId());
		if(reservationExistante.getUser().equals(user)) {
			return true;
		}
		return false;
	}
	
	private User getCurrentUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
