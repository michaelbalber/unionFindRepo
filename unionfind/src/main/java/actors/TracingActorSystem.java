package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public enum TracingActorSystem {
	ACTOR_SYSTEM;
	private ActorSystem actorStsyem;

	private TracingActorSystem() {
		actorStsyem = ActorSystem.create("tracingSystem");
	}
	
	public ActorSystem getActorSystem() {
		return actorStsyem;
	}
	
	public ActorRef getActorRefByName(String actorName) {
		return getActorSystem().actorFor("user/"+actorName);
	}
}
