package balber.reactive.actors;

import akka.actor.ActorSystem;

public enum GlobalActorSystem {
	ACTOR_SYSTEM;
	
	private ActorSystem system = ActorSystem.create("MySystem");	
	public ActorSystem getSystem() {
		return system;
	}
}
