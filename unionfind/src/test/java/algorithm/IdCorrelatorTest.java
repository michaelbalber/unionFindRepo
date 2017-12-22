package algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class IdCorrelatorTest {

	@Test
	void testBasicAddEvents() {
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
	@Test
	void testDeleteAndInsert() {
		IdCorrelator correlator = new IdCorrelator();
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
		IdCorrelator correlator = new IdCorrelator();
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
		int cycles = 50;
		IdCorrelator correlator = new IdCorrelator();
		for(int j=0;j<cycles ; j++) {
			ArrayList<Event> eventsList = new ArrayList<Event>();
			ArrayList<Integer> deleteIdsList = new ArrayList<Integer>();
			createLoadData(j,eventsList,deleteIdsList);
			System.out.println("start Time: " + System.nanoTime());
			long startNanoTime = System.nanoTime();
			for (Event event : eventsList) {
				if(event.getCorrelationId()==-1) {
					correlator.addEvent(event);
				}else {
					correlator.addCorrelation(event.getId(), event.getCorrelationId());
				}
			}
			for (Integer id : deleteIdsList) {
				correlator.deleteGroupOfEvent(new Event(id));
			} 
			double durtion = (System.nanoTime() - startNanoTime)/1000000.0;
			System.out.println("duration millis: " + durtion);
			correlator.printUnionFind();
			Thread.sleep(1000);
		}
	}

	private void createLoadData(int index, List<Event> eventlist, List<Integer> deleteIdsList){
		int maxEvents = 10;
		int maxTracks = 1500;
		//ArrayList<Event> list = new ArrayList<>();
		for(int i=0+maxTracks*index ; i<maxTracks*(index+1) ; i++) {
			for(int j=0 ; j<maxEvents ; j++) {
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