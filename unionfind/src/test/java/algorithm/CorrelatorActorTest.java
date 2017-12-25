package algorithm;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import actors.DeleteGroupRequest;
import actors.IdCorrelatorManager;
import actors.TracingActorSystem;
import akka.actor.ActorRef;

class CorrelatorActorTest {

	@Test
	void testLoad() throws InterruptedException{
		int cycles = 50000;
		IdCorrelatorManager.initActors();
		ActorRef correlatorActor = TracingActorSystem.ACTOR_SYSTEM.getActorSystem().actorFor("user/CorrelatorActor");
		for(int j=0;j<cycles ; j++) {
			long startNanoTime = System.nanoTime();
			ArrayList<Event> eventsList = new ArrayList<Event>();
			ArrayList<Integer> deleteIdsList = new ArrayList<Integer>();
			IdCorrelatorTest.createLoadData(j,eventsList,deleteIdsList);
			for (Event event : eventsList) {
				event.setTimestamp(System.nanoTime());
				correlatorActor.tell(event,ActorRef.noSender());
			}
			if(j >20) {
				for (Integer id : deleteIdsList) {
					correlatorActor.tell(new DeleteGroupRequest(new Event(id)), ActorRef.noSender());
				} 
			}
//			double durtion = (System.nanoTime() - startNanoTime)/1000000.0;
//			System.out.println("duration millis: " + durtion+" cycle: "+j);
//			correlator.printUnionFind();
//			System.out.println(correlator.getEventsMap());
			System.out.println("cycles: "+j);
			Thread.sleep(900);
		}
	}


}
