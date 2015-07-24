package ch.waterbead.models;

import java.util.ArrayList;
import java.util.List;


public class EventUtil {
	private static final String PRIVATE = "blue";
	private static final String NON_PRIVATE = "red";
	
	public static List<Event> toEvents(List<Reservation> reservations) {
		List<Event> events = new ArrayList<>();
		reservations.forEach(r -> {
			Event event = new Event(r.getId(), r.getTitle(), r.isPrivacy()?PRIVATE:NON_PRIVATE , r.getPeriod().getFrom().toString(), r.getPeriod().getTo().plusDays(1).toString());
			events.add(event);
		});
		return events;
	}
}