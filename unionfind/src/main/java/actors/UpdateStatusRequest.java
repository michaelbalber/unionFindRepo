package actors;

import java.util.List;

import algorithm.Event;

public class UpdateStatusRequest {
	private final List<Event>  eventList;
	public UpdateStatusRequest(List<Event>  events) {
		eventList = events;
	}
	
	public List<Event> getEventList() {
		return eventList;
	}
}
