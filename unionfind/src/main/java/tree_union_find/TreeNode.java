package tree_union_find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeNode<T>{
	private T data = null;
	private List<TreeNode<T>> children = new ArrayList<>();
	private TreeNode<T> parent = null;

	public TreeNode(T data) {
		this.data = data;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		TreeNode other = (TreeNode) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}



	public void addChild(TreeNode<T> child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(T data) {
		TreeNode<T> newChild = new TreeNode<>(data);
		newChild.setParent(this);
		children.add(newChild);
	}

	public void addChildren(List<TreeNode<T>> children) {
		for(TreeNode<T> t : children) {
			t.setParent(this);
		}
		this.children.addAll(children);
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public TreeNode<T> getParent() {
		return parent;
	}
	
	public void removeChild(TreeNode<T> child) {
		this.children.remove(child);
	}

	public static void main(String[] args) {

		TreeNode<String> root = new TreeNode<>("Root");

		TreeNode<String> child1 = new TreeNode<>("Child1");
		child1.addChild("Grandchild1");
		child1.addChild("Grandchild2");

		TreeNode<String> child2 = new TreeNode<>("Child2");
		child2.addChild("Grandchild3");

		root.addChild(child1);
		root.addChild(child2);
		root.addChild("Child3");

		root.addChildren(Arrays.asList(
				new TreeNode<String>("Child4"),
				new TreeNode<String>("Child5"),
				new TreeNode<String>("Child6")
				));

		for(TreeNode<String> node : root.getChildren()) {
			System.out.println(node.getData());
		}
	}



	public Set<T> getAllSubTree() {
		HashSet<T> resultSet = new HashSet<T>();
		BlockingQueue<TreeNode<T>> q = new LinkedBlockingQueue<TreeNode<T>>();
		q.add(this);
		while(!q.isEmpty()) {
			TreeNode<T> node = q.poll();
			resultSet.add(node.getData());
			q.addAll(node.getChildren());
		}
			
		return resultSet;
		// TODO Auto-generated method stub
		
	}

}
//Some examples:
