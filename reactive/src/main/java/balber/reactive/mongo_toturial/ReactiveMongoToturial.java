package balber.reactive.mongo_toturial;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.reactivestreams.Publisher;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.Success;

import akka.actor.ActorSystem;
import balber.reactive.actors.GlobalActorSystem;

/**
 * Hello world!
 *
 */
public class ReactiveMongoToturial 
{
    public static void main( String[] args ) throws Throwable
    {
    	ActorSystem system = GlobalActorSystem.ACTOR_SYSTEM.getSystem();
    	MongoClient mongoClient = MongoClients.create();
    	MongoDatabase database = mongoClient.getDatabase("mydb");
    	MongoCollection<Document> collection = database.getCollection("testData");
    	Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
    	Publisher<Success> publisher = collection.insertOne(doc);//;.subscribe(new OperationSubscriber<Success>());
    	publisher.subscribe(new OperationSubscriber<Success>());
    	List<Document> documents = new ArrayList<>();
    	for (int i = 0; i < 100; i++) {
    	    documents.add(new Document("i", i));
    	}
    	
    	ObservableSubscriber<Success> subscriber = new ObservableSubscriber<>();
    	collection.insertMany(documents).subscribe(subscriber);
    	subscriber.await();

    	collection.count()
        .subscribe(new PrintSubscriber<Long>("total # of documents after inserting "
                                            + " 100 small ones (should be 101): %s"));

    }
}
