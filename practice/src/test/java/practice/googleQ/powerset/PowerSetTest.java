package practice.googleQ.powerset;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import practice.googleQ.powerset.PowerSet;

public class PowerSetTest {

	@Test
	public void test() {
		PowerSet powerSet = new PowerSet();
		List<List<Integer>> res = powerSet.createPowerSet(Arrays.asList(1,4,6,7));
		System.out.println(res);
		assertEquals(16, res.size());
	}
	
	@Test
	public void testEmpty() {
		PowerSet powerSet = new PowerSet();
		List<List<Integer>> res = powerSet.createPowerSet(Arrays.asList());
		System.out.println(res);
		assertEquals(1, res.size());
	}

}
