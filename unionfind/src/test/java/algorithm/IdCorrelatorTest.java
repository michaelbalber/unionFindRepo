package algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actors.DeleteGroupRequest;
import actors.Event;
import actors.IdCorrelatorManager;
import actors.TracingActorSystem;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import regular_union_find.ICorrelator;
import tree_union_find.IdCorrelatorTree;

class IdCorrelatorTest {

	private ICorrelator correlator;

	@BeforeEach
	public void before() {
		correlator = new IdCorrelatorTree();
	}
	@Test
	void testBasicAddEvents() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));

		List<Event> actual = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1), actual.stream().map(x->x.getId()).collect(Collectors.toList()));

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

		List<Event> actual3 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(3), actual3.stream().map(x->x.getId()).collect(Collectors.toList()));

	}

	@Test
	void testBasicCorrelation() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		List<Event> actual = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1), actual.stream().map(x->x.getId()).collect(Collectors.toList()));

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(2,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testTwoCorrelations() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.addCorrelation(1, 3);

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(1,2,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testTwoCorrelations2() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addCorrelation(1, 2);
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(1,2,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testCorrelationBeforeEvent() {
		correlator.addEvent(new Event(1));
		correlator.addCorrelation(1, 2);
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(1,2,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testTwoEventsSameId() {
		correlator.addCorrelation(1, 2);
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.addEvent(new Event(3));

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(1,2,3,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testTwoEventsSameId2() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.addEvent(new Event(3));

		List<Event> actual = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1), actual.stream().map(x->x.getId()).collect(Collectors.toList()));		

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(2,3,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testAddNoExistsCorrelationId() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(5, 4);


		List<Event> actual = correlator.getGroupOfEvent(new Event(4));
		assertTrue(actual.isEmpty());		

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(2,3,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}

	@Test
	void testBasicDelete() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.deleteGroupOfEvent(new Event(3));

		List<Event> actual = correlator.getGroupOfEvent(new Event(2));
		assertTrue(actual.isEmpty());		

		List<Event> actual2 = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		

	}
	//@Test
	void testDeleteAndInsert() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.deleteGroupOfEvent(new Event(3));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));

		List<Event> actual = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1), actual.stream().map(x->x.getId()).collect(Collectors.toList()));	
		List<Event> actual2 = correlator.getGroupOfEvent(new Event(2));
		assertEquals(Arrays.asList(2), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));	

		List<Event> actual3 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(3), actual3.stream().map(x->x.getId()).collect(Collectors.toList()));	

	}

	@Test
	void testDeleteAndInsert2() {
		correlator.addEvent(new Event(1));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		correlator.deleteGroupOfEvent(new Event(3));
		correlator.addEvent(new Event(2));
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(1, 3);

		List<Event> actual = correlator.getGroupOfEvent(new Event(1));
		assertEquals(Arrays.asList(1,3), actual.stream().map(x->x.getId()).collect(Collectors.toList()));	
		List<Event> actual2 = correlator.getGroupOfEvent(new Event(2));
		assertEquals(Arrays.asList(2), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));	

	}

	@Test
	void testLoad() throws InterruptedException{
		int cycles = 50000;
		for(int j=0;j<cycles ; j++) {
			ArrayList<Event> eventsList = new ArrayList<Event>();
			ArrayList<Integer> deleteIdsList = new ArrayList<Integer>();
			createLoadData(j,eventsList,deleteIdsList);
			//System.out.println("start Time: " + System.nanoTime());
			long startNanoTime = System.nanoTime();
			for (Event event : eventsList) {
				if(event.getCorrelationId()==-1) {
					correlator.addEvent(event);
				}else {
					correlator.addCorrelation(event.getId(), event.getCorrelationId());
				}
			}
			if(j >10) {
				for (Integer id : deleteIdsList) {
					correlator.deleteGroupOfEvent(new Event(id));
				} 
				double durtion = (System.nanoTime() - startNanoTime)/1000000.0;
				System.out.println("duration millis: " + durtion+" cycle: "+j);
			}
//			correlator.printUnionFind();
//			System.out.println(correlator.getEventsMap());
			Thread.sleep(10);
		}
	}

	public static void createLoadData(int index, List<Event> eventlist, List<Integer> deleteIdsList){
		int maxEvents = 10;
		int maxTracks = 10000;
		//ArrayList<Event> list = new ArrayList<>();
		for(int i=0+maxTracks*index ; i<maxTracks*(index+1) ; i++) {
			for(int j=0 ; j<maxEvents ; j++) {
				eventlist.add(new Event(maxEvents*i+j));
				eventlist.add(new Event(maxEvents*i+j));
				if(j>0) {
					eventlist.add(new Event(maxEvents*i+j-1,maxEvents*i+j));
				}
			}
			deleteIdsList.add(maxEvents*i);
		}
		Collections.shuffle(eventlist);
	}


}