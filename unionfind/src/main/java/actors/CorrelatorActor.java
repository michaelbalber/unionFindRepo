package actors;

import akka.actor.AbstractActor;
import algorithm.Event;
import algorithm.ICorrelator;
import tree_union_find.IdCorrelatorTree;

public class CorrelatorActor extends AbstractActor{

	private static final boolean SHOULD_SAMPLE_LATENCY = true;
	private ICorrelator correlator = new IdCorrelatorTree();

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Event.class,this::dealWithEvent)
				.match(DeleteGroupRequest.class,this::dealWithDelete)
				.build();
	}

	public void dealWithEvent(Event event) {
	
		if(event.getCorrelationId()!=-1) {
			correlator.addCorrelation(event.getId(), event.getCorrelationId());
		}else {
			correlator.addEvent(event);
		}
		sampledLatency(event,"add");
	}

	private void sampledLatency(Event event, String actionStr) {
		if(!SHOULD_SAMPLE_LATENCY) {
			return;
		}
		double duration = (System.nanoTime() - event.getTimestamp())/1000000.0;
		if(System.nanoTime()%10000==1)
			System.out.println(actionStr+" actor latency: "+duration);
	}

	public void dealWithDelete(DeleteGroupRequest deleteRequest) {
		correlator.deleteGroupOfEvent(deleteRequest.getEvent());
		sampledLatency(deleteRequest.getEvent(),"delete");
	}

}
