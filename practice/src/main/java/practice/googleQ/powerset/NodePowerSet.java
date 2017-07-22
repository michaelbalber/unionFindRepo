package practice.googleQ.powerset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodePowerSet<T> {
	
	private final NodePowerSet<T> parent;
	private final Optional<T> value;
	private List<NodePowerSet<T>> children = new ArrayList<NodePowerSet<T>>();
	
	public NodePowerSet(NodePowerSet<T> parentNode, T val){
		parent = parentNode;
		value = Optional.ofNullable(val);
	}
	
	public void addChild(T val) {
		NodePowerSet<T> node = new NodePowerSet<T>(this,val);
		children.add(node);
	}
	
	public void addAllChildren(List<T> values) {
		for (T val : values) {
			NodePowerSet<T> node = new NodePowerSet<T>(this,val);
			children.add(node);
		}
	}

	public NodePowerSet<T> getParent() {
		return parent;
	}

	public List<NodePowerSet<T>> getChildren() {
		return children;
	}

	public Optional<T> getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	
}
