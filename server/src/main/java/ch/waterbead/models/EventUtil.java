package ch.waterbead.models;

import java.util.ArrayList;
import java.util.List;


public class EventUtil {
	private static final String PRIVATE = "blue";
	private static final String NON_PRIVATE = "red";
	
	public static List<Event> toEvents(List<Reservation> reservations) {
		List<Event> events = new ArrayList<>();
		reservations.forEach(r -> {
			String title = r.getPeriod().getFrom().toString();
			Event event = new Event(r.getId(), title, r.isPrivate()?PRIVATE:NON_PRIVATE , r.getPeriod().getFrom().toString(), r.getPeriod().getTo().toString());
			events.add(event);
		});
		return events;
	}
}
