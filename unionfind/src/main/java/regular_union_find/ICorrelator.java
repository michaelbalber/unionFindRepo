package regular_union_find;

import java.util.List;
import java.util.Set;

import entities.Event;

public interface ICorrelator {

	void addEvent(Event event);

	void addCorrelation(int i, int j);

	List<Event> getGroupOfEvent(Event event);

	Set<Integer> deleteGroupOfEvent(Event event);

	int getNumberOfUniqueIDs();

}
