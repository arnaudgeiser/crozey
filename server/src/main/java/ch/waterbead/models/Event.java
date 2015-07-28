package ch.waterbead.models;

public class Event {
	private final long id;
	private final String title;
	private final String color;
	private final String start;
	private final String end;
	private final String username;
	private boolean isPrivate = false;
	private boolean allDay = true;
	
	public Event(long id, String title, String color, String start, String end, String username) {
		super();
		this.id = id;
		this.title = title;
		this.color = color;
		this.start = start;
		this.end = end;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getColor() {
		return color;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public String getUsername() {
		return username;
	}
}
