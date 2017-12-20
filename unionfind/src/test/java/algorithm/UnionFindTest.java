package algorithm;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class UnionFindTest {

	@Test
	void test() {
		HashSet<Integer> set = new HashSet<Integer>();
		set.addAll(Arrays.asList(1,2,3,4,5,6));
		
		UnionFind<Integer> unionFind = new UnionFind<>(set);
		System.out.println(unionFind.toString());
		unionFind.union(2, 3);
		
		System.out.println(unionFind.toString());
		unionFind.union(4, 5);
		
		System.out.println(unionFind.toString());
		unionFind.union(3, 4);
		
		System.out.println(unionFind.toString());
	}
	
	@Test
	void test2() {
		HashSet<Event> set = new HashSet<Event>();
		set.addAll(Arrays.asList(new Event(1),new Event(2),new Event(3)
				,new Event(4),new Event(5),new Event(6)));
		
		UnionFind<Event> unionFind = new UnionFind<>(set);
		System.out.println(unionFind.toString());
		unionFind.union(new Event(2), new Event(3));
		
		System.out.println(unionFind.toString());
		unionFind.union(new Event(4), new Event(5));
		
		System.out.println(unionFind.toString());
		unionFind.union(new Event(3), new Event(4));
		
		System.out.println(unionFind.toString());
	}

	@Test
	void addEventstest() {
		HashSet<Event> set = new HashSet<Event>();
		set.addAll(Arrays.asList(new Event(1),new Event(2),new Event(3)
				,new Event(4),new Event(6)));
		
		UnionFind<Event> unionFind = new UnionFind<>(set);
		System.out.println(unionFind.toString());
		unionFind.union(new Event(2), new Event(3));
		
		System.out.println(unionFind.toString());
		unionFind.addElement(new Event(5));
		unionFind.union(new Event(4), new Event(5));
		
		System.out.println(unionFind.toString());
		unionFind.union(new Event(3), new Event(4));
		
		System.out.println(unionFind.toString());
		
	
	}
}
