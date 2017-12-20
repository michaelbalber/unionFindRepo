package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdCorrelator {
	private UnionFind<Integer> unionFind = new UnionFind<>(new HashSet<Integer>());
	HashMap<Integer,List<Event>> eventsMap=new HashMap<>();
		
	
	public void addEvent(Event event) {
		unionFind.addElement(event.getId());
		
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
