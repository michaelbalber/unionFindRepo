package practice.googleQ.powerset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



public class PowerSet {
	
	private  NodePowerSet<Integer> root;
	
	public PowerSet() {
		root = new NodePowerSet<Integer>(null,null);	
	}

	public List<List<Integer>> createPowerSet(List<Integer> list){
		
		if(list.isEmpty()) {
			List<Integer> empty = new ArrayList<>();
			List<List<Integer>> res = new ArrayList<>();
			res.add(empty);
			return res;
		}
		Collections.sort(list);
		root.addChild(list.get(0));
		root.addChild(null);
		List<NodePowerSet<Integer>> childrenInRaw = root.getChildren();
		
		for (int i=1 ; i<list.size(); i++) {
			Integer val = list.get(i);
			ArrayList<NodePowerSet<Integer>> tempChildrenInRaw = new ArrayList<NodePowerSet<Integer>>();
			for (NodePowerSet<Integer> node : childrenInRaw) {
				node.addChild(val);
				node.addChild(null);
				tempChildrenInRaw.addAll(node.getChildren());
			}
			
			childrenInRaw = tempChildrenInRaw;
		
		}
		
		List<List<Integer>> powerSet = 
				childrenInRaw.stream().
							map(this::nodeToSubSet).
							collect(Collectors.toList());
						
		return powerSet;
		
	}
	
	public List<Integer> nodeToSubSet(NodePowerSet<Integer> node){
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(node!=null) {
			node.getValue().ifPresent(x -> list.add(x));
			node = node.getParent();
		}
		return list;
	}
}
