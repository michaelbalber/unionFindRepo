package actors;

import java.util.List;

import algorithm.Event;

public class UpdateStatus {
	private final List<Event>  eventList;
	public UpdateStatus(List<Event>  events) {
		eventList = events;
	}
	
	public List<Event> getEventList() {
		return eventList;
	}
}
