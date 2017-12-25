package actors;

import algorithm.Event;

public class DeleteGroupRequest {
	private Event event;

	public DeleteGroupRequest(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}
	
}
