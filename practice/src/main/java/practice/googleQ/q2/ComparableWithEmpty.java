package practice.googleQ.q2;

public class ComparableWithEmpty<T> implements Comparable<T> {

	@Override
	public int compareTo(T o) {
		if (o instanceof EmptyVal) {
			return -1;
		}
		
		return this.compareTo(o); 
	}
	
}
