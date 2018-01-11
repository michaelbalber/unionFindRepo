package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

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
	
	public static void initActors() {
		ActorSystem system = ACTOR_SYSTEM.getActorSystem();

	    system.actorOf(Props.create(CorrelatorActor.class), "CorrelatorActor");
	    system.actorOf(Props.create(StatusUpdaterActor.class), "StatusUpdaterActor");	    
	    
	}
}
