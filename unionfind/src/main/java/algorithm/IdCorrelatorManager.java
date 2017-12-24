package algorithm;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class IdCorrelatorManager {
	public void initActors() {
		ActorSystem system = ActorSystem.create("testSystem");

	    ActorRef firstRef = system.actorOf(Props.create(CorrelatorActor.class), "first-actor");

	}
}
