package algorithm;

public class Event {
	private final int id;
	private final int correlationId;
	private long timestamp = System.nanoTime();
	
	

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Event(int n) {
		id = n;
		correlationId = -1;
	}
	
	public Event(int n, int correlation) {
		id = n;
		correlationId = correlation;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public int getId() {
		return id;
	}
	
	public int getCorrelationId() {
		return correlationId;
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
