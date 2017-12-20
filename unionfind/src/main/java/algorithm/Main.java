package algorithm;

import java.util.Arrays;
import java.util.HashSet;

public class Main {
	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<Integer>();
		set.addAll(Arrays.asList(1,2,3,4,5,6));
		
		UnionFind unionFind = new UnionFind<>(set);
		System.out.println(unionFind.toString());
		unionFind.union(2, 3);
		
		System.out.println(unionFind.toString());
		unionFind.union(4, 5);
		
		System.out.println(unionFind.toString());
		unionFind.union(3, 4);
		
		System.out.println(unionFind.toString());
		
	}
}
