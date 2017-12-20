package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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
		correlator.addEvent(new Event(2));
		correlator.addCorrelation(1, 2);
		correlator.addEvent(new Event(3));
		correlator.addCorrelation(2, 3);
		
		List<Event> actual2 = correlator.getGroupOfEvent(new Event(3));
		assertEquals(Arrays.asList(1,2,3), actual2.stream().map(x->x.getId()).collect(Collectors.toList()));		
		
	}
}
