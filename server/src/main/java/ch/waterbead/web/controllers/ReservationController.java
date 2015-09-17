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
	public List<Event> feed(@RequestParam("start") String start, @RequestParam("end") String end) {
		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);
		List<Reservation> reservations = reservationRepository.findByMonthAndYear(startDate, endDate);
		return EventUtil.toEvents(reservations);
	}
	
	@RequestMapping(consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public Response add(@RequestBody Reservation reservation, HttpServletRequest req) {
		reservationService.add(reservation);
		return Response.ok();
	}
	
	@RequestMapping(value="/{id}",consumes="application/json",produces="application/json",method=RequestMethod.PUT)
	public Response update(@RequestBody Reservation reservation) {
		reservationService.update(reservation);
		return Response.ok();
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Response delete(@PathVariable(value="id") long id) {
		reservationService.deleteById(id);
		return Response.ok();
	}
}
