package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class IdCorrelatorManager {
	public static void initActors() {
		ActorSystem system = TracingActorSystem.ACTOR_SYSTEM.getActorSystem();

	    ActorRef firstRef = system.actorOf(Props.create(CorrelatorActor.class), "CorrelatorActor");
	    
	    
	    
	    
	}
}
