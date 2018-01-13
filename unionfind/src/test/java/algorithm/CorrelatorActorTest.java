package algorithm;

import static akka.pattern.PatternsCS.ask;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;

import actors.TracingActorSystem;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import entities.DeleteGroupRequest;
import entities.Event;
import scala.concurrent.duration.Duration;

public class CorrelatorActorTest {

	ActorRef correlatorActor;
	
	
	@Before
	public void before() {
		TracingActorSystem.ACTOR_SYSTEM.initActors();		
		correlatorActor = TracingActorSystem.ACTOR_SYSTEM.getActorRefByName("CorrelatorActor");
	}

	@After
	public void after() {
		TracingActorSystem.ACTOR_SYSTEM.uninitActors();		
	}
	
	
	public void test2() {
		Event e1 = new Event(1);
		Event e2 = new Event(1,2);
		Event e3 = new Event(2);
		Event e4 = new Event(2,3);
		Event e5 = new Event(3);
		
		correlatorActor.tell(e1, ActorRef.noSender());
		correlatorActor.tell(e2, ActorRef.noSender());
		correlatorActor.tell(e3, ActorRef.noSender());
		correlatorActor.tell(e4, ActorRef.noSender());
		correlatorActor.tell(e5, ActorRef.noSender());
		ask(correlatorActor, "getEventsMap", 1000);
		ask(correlatorActor, "getUnionFind", 1000);
		
	}
	
	//@Test
	public void testsystem1() {
		
		ActorSystem system = TracingActorSystem.ACTOR_SYSTEM.getActorSystem();
		system.scheduler().schedule(Duration.Zero(),
				Duration.create(50, TimeUnit.MILLISECONDS), correlatorActor, new Event(7),
				system.dispatcher(), null);
	}
	
	//@Test
	public void testLoad() throws InterruptedException{
		int cycles = 50000;
		for(int j=0;j<cycles ; j++) {
			long startNanoTime = System.nanoTime();
			ArrayList<Event> eventsList = new ArrayList<Event>();
			ArrayList<Integer> deleteIdsList = new ArrayList<Integer>();
			IdCorrelatorTest.createLoadData(j,eventsList,deleteIdsList);
			for (Event event : eventsList) {
				event.setcreationTimestamp(System.nanoTime());
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
