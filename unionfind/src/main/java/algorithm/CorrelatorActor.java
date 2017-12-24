package algorithm;

import java.util.LinkedList;
import java.util.Optional;

import akka.actor.AbstractActor;

public class CorrelatorActor extends AbstractActor{

	private static final int NUNBER_OF_CORRELATORS = 10;
	private static final int MAX_EVENTS_IN_CORRELATOR = 0;
	private LinkedList<IdCorrelator> correlatorsList = new LinkedList<IdCorrelator>();

	public CorrelatorActor() {
		//		for(int i=0; i<NUNBER_OF_CORRELATORS ; i++) {
		//		}
		this.correlatorsList .add(new IdCorrelator());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Event.class,this::dealWithEvent)
				.match(DeleteGroupRequest.class,this::dealWithDelete)
				.build();
	}

	public void dealWithEvent(Event event) {
		IdCorrelator correlator = correlatorsList.stream()
				.filter(idCorrelator->idCorrelator.contains(event))
				.findFirst()
				.orElse(correlatorsList.stream()
						.filter(idCorrelator->idCorrelator.getEventsMap().size() < MAX_EVENTS_IN_CORRELATOR)
						.findFirst().get());

		if(correlator==null) { // an event
			correlator = new IdCorrelator();
			correlatorsList.add(correlator);
		}

		if(event.getCorrelationId()!=-1) {
			correlator.addCorrelation(event.getId(), event.getCorrelationId());
		}else{//regular event
			correlator.addEvent(event); 
		}
	}

	public void dealWithDelete(DeleteGroupRequest delete) {

	}

}
