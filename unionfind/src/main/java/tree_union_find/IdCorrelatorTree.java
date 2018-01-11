package tree_union_find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.Event;
import regular_union_find.ICorrelator;

public class IdCorrelatorTree implements ICorrelator{
	//The union find set - has only the entety ids. one copy of each id.
	private TreeUnionFind<Integer> unionFind = new TreeUnionFind<>(new HashSet<Integer>());
	//The actual events. It is needed because the union find has only one copy of each id.
	HashMap<Integer,List<Event>> eventsMap=new HashMap<>();
		
	
	public HashMap<Integer, List<Event>> getEventsMap() {
		return eventsMap;
	}

	public void addEvent(Event event) {
		unionFind.addElement(event.getId());
		
		//add to the map of events
		List<Event> resEvents = eventsMap.get(event.getId());
		if(resEvents==null || resEvents.isEmpty()) {
			List<Event> list = new ArrayList<>();
			list.add(event);
			eventsMap.put(event.getId(),list);
		}else {
			resEvents.add(event);
		}
	}
	
	public void addCorrelation(int id1, int id2) {
		//it adds the two ids of the correlation even if not both of them already exists.
		//it is possible because the real events are in the eventsMap
		unionFind.addElement(id1);
		unionFind.addElement(id2);
		unionFind.union(id1, id2);
	}
	
	public List<Event> getGroupOfEvent(Event event){
		ArrayList<Event> resEvents = new ArrayList<Event>();
		Set<Integer> allIds = unionFind.getGroupOfElement(unionFind.find(event.getId()));
		if(allIds==null) {
			return resEvents;
		}
		for (Integer id : allIds) {
			List<Event> eventList = eventsMap.get(id);
			if(eventList!=null) {
				resEvents.addAll(eventList);
			}
		}
		return resEvents;
	}
	
	public Set<Integer> deleteGroupOfEvent(Event event){
		Set<Integer> set = unionFind.deleteGroupofElement(event.getId());
		for (Integer key : set) {
			eventsMap.remove(key);
		}
		return set;
	}
	
}
