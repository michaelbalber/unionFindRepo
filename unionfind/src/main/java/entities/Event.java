package entities;

public class Event {
	private static final int NOT_CORRELATION_ID = -1;
	private final int id;
	private final int correlationId;
	private long creationTimestamp = System.nanoTime();
	private EEventTimeLine eventTimeLine = EEventTimeLine.MIDDLE;
	




	public Event(int n) {
		id = n;
		correlationId = NOT_CORRELATION_ID;
	}
	
	public Event(int n, int correlation) {
		id = n;
		correlationId = correlation;
	}

	public void setcreationTimestamp(long timestamp) {
		this.creationTimestamp = timestamp;
	}
	
	public void setEventTimeLine(EEventTimeLine eventTimeLine) {
		this.eventTimeLine = eventTimeLine;
	}
	
	public EEventTimeLine getEventTimeLine() {
		return eventTimeLine;
	}

	public boolean isCorrelation() {
		return 	correlationId != NOT_CORRELATION_ID;
	}
	
	public long getTimestamp() {
		return creationTimestamp;
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
