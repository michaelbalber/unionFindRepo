package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public enum TracingActorSystem {
	ACTOR_SYSTEM;
	private ActorSystem actorSystem;

	private TracingActorSystem() {
		actorSystem = ActorSystem.create("tracingSystem");
	}
	
	public ActorSystem getActorSystem() {
		return actorSystem;
	}
	
	public ActorRef getActorRefByName(String actorName) {
		return getActorSystem().actorFor("user/"+actorName);
	}
	
	public void initActors() {

	    actorSystem.actorOf(Props.create(CorrelatorActor.class), "CorrelatorActor");
	    actorSystem.actorOf(Props.create(StatusUpdaterActor.class), "StatusUpdaterActor");	    
	    
	}
	
	public void uninitActors() {
		getActorRefByName("CorrelatorActor").tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
		getActorRefByName("StatusUpdaterActor").tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	}
}
