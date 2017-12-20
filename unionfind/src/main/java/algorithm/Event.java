package algorithm;

public class Event {
	int id;
	
	public Event(int n) {
		id = n;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
        return ""+id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
