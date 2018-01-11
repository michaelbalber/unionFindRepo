package actors;

import java.util.List;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import entities.DeleteGroupRequest;
import entities.Event;
import entities.UpdateStatusRequest;
import regular_union_find.ICorrelator;
import tree_union_find.IdCorrelatorTree;

public class CorrelatorActor extends AbstractActor{

	private static final boolean SHOULD_SAMPLE_LATENCY = false;
	private final ICorrelator correlator = new IdCorrelatorTree();
	private final ActorRef statusUpdaterActor = TracingActorSystem.ACTOR_SYSTEM.getActorRefByName("StatusUpdaterActor");


	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Event.class,this::dealWithEvent)
				.match(DeleteGroupRequest.class,this::dealWithDelete)
				.build();
	}

	/**
	 *  @param event - event can be correlation or an event. 
	 *  if we want correlation to be also an event we should change the function.
	 */
	public void dealWithEvent(Event event) {
	
		if(event.isCorrelation()) {
			correlator.addCorrelation(event.getId(), event.getCorrelationId());
		}else {
			correlator.addEvent(event);
		}
		List<Event> groupOfEvent = correlator.getGroupOfEvent(event);
		if(groupOfEvent!=null && groupOfEvent.size()>1) {
			statusUpdaterActor.tell(new UpdateStatusRequest(groupOfEvent), getSelf());
		}
		sampledLatency(event,"add");
	}

	public void dealWithDelete(DeleteGroupRequest deleteRequest) {
		correlator.deleteGroupOfEvent(deleteRequest.getEvent());
		sampledLatency(deleteRequest.getEvent(),"delete");
	}

	private void sampledLatency(Event event, String actionStr) {
		if(!SHOULD_SAMPLE_LATENCY) {
			return;
		}
		double duration = (System.nanoTime() - event.getCreationTimestamp())/1000000.0;
		if(System.nanoTime()%10000==1)
			System.out.println(actionStr+" actor latency: "+duration);
	}

}
