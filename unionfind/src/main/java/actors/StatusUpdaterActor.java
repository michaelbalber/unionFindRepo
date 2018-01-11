package actors;

import java.util.List;
import java.util.stream.Collectors;

import akka.actor.AbstractActor;
import entities.Event;
import entities.UpdateStatusRequest;

public class StatusUpdaterActor extends AbstractActor{

	private static final boolean SHOULD_SAMPLE_LATENCY = true;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(UpdateStatusRequest.class,this::updateStatus)
				.build();
	}
	
	public void updateStatus(UpdateStatusRequest update) {
		List<Event> events = update.getEventList();
		List<Long> sorted = events.stream().map(x->x.getCreationTimestamp()).sorted().collect(Collectors.toList());
		long delta = sorted.get(sorted.size()-1)- sorted.get(0);
		if(!SHOULD_SAMPLE_LATENCY) {
			return;
		}
		if(System.nanoTime()%10000==1)
			System.out.println("updateStatus: " + delta);

	}
}
