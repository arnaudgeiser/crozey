package ch.waterbead.models;

import java.util.ArrayList;
import java.util.List;


public class EventUtil {
	public static List<Event> toEvents(List<Reservation> reservations) {
		List<Event> events = new ArrayList<>();
		reservations.forEach(r -> {
			Event event = new Event(r.getId(), r.getTitle(), r.getPeriod().getFrom().toString(), r.getPeriod().getTo().plusDays(1).toString(), r.getUser().getFirstNameLastName(), r.isPrivacy());
			events.add(event);
		});
		return events;
	}
}
