package tree_union_find;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * An implementation of <a href="http://en.wikipedia.org/wiki/Disjoint-set_data_structure">Union
 * Find</a> data structure. Union Find is a disjoint-set data structure. It supports two operations:
 * finding the set a specific element is in, and merging two sets. The implementation uses union by
 * rank and path compression to achieve an amortized cost of O(a(n)) per operation where a is the
 * inverse Ackermann function. UnionFind uses the hashCode and equals method of the elements it
 * operates on.
 *
 * @param <T> element type
 *
 * @author Tom Conerly
 * @since Feb 10, 2010
 */
public class TreeUnionFind<T>
{
	private final Map<T, TreeNode<T>> nodeMap;
	private final Map<T, Integer> rankMap;
	private int count; // number of components

	/**
	 * Creates a UnionFind instance with all the elements in separate sets.
	 * 
	 * @param elements the initial elements to include (each element in a singleton set).
	 */
	public TreeUnionFind(Set<T> elements)
	{
		nodeMap = new WeakHashMap<>();
		rankMap = new WeakHashMap<>();
		for (T element : elements) {
			addElement(element);
		}
		count = elements.size();
	}

	/**
	 * Adds a new element to the data structure in its own set.
	 *
	 * @param element The element to add.
	 */
	public void addElement(T element)
	{
		if (isElementIncluded(element))
			return;
		nodeMap.put(element, new TreeNode<T>(element));
		rankMap.put(element, 0);
		count++;
	}


	/**
	 * @return map from element to parent element
	 */
//	protected Map<T, T> getParentMap()
//	{
//		return nodeMap;
//	}

	/**
	 * @return map from element to rank
	 */
	protected Map<T, Integer> getRankMap()
	{
		return rankMap;
	}

	/**
	 * Returns the representative element of the set that element is in.
	 *
	 * @param element The element to find.
	 *
	 * @return The element representing the set the element is in.
	 */
	public T find(final T element)
	{
		if (!isElementIncluded(element)) {
			return null;
		}

		T current = element;
		while (true) {
			T parent = getParentData(current);
			if (parent.equals(current)) {
				break;
			}
			current = parent;
		}
		final T root = current;

		current = element;
		while (!current.equals(root)) {
			T parent = getParentData(current);
			setParent(current, root);
			current = parent;
		}

		return root;
	}

	/**
	 * Merges the sets which contain element1 and element2. No guarantees are given as to which
	 * element becomes the representative of the resulting (merged) set: this can be either
	 * find(element1) or find(element2).
	 *
	 * @param element1 The first element to union.
	 * @param element2 The second element to union.
	 */
	public void union(T element1, T element2)
	{
		if (!isElementIncluded(element1) || !isElementIncluded(element2)) {
			throw new IllegalArgumentException("elements must be contained in given set");
		}

		T parent1 = find(element1);
		T parent2 = find(element2);

		// check if the elements are already in the same set
		if (parent1.equals(parent2)) {
			return;
		}

		int rank1 = rankMap.get(parent1);
		int rank2 = rankMap.get(parent2);
		if (rank1 > rank2) {
			setParent(parent2, parent1);
		} else if (rank1 < rank2) {
			setParent(parent1, parent2);
		} else {
			setParent(parent2, parent1);
			rankMap.put(parent1, rank1 + 1);
		}
		count--;
	}

	/**
	 * Tests whether two elements are contained in the same set.
	 * 
	 * @param element1 first element
	 * @param element2 second element
	 * @return true if element1 and element2 are contained in the same set, false otherwise.
	 */
	public boolean inSameSet(T element1, T element2)
	{
		return find(element1).equals(find(element2));
	}

	/**
	 * Returns the number of sets. Initially, all items are in their own set. The smallest number of
	 * sets equals one.
	 * 
	 * @return the number of sets
	 */
	public int numberOfSets()
	{
		assert count >= 1 && count <= nodeMap.keySet().size();
		return count;
	}

	/**
	 * Returns the total number of elements in this data structure.
	 * 
	 * @return the total number of elements in this data structure.
	 */
	public int size()
	{
		return nodeMap.size();
	}

	/**
	 * Resets the UnionFind data structure: each element is placed in its own singleton set.
	 */
	public void reset()
	{
		for (T element : nodeMap.keySet()) {
			setParent(element, element);
			rankMap.put(element, 0);
		}
		count = nodeMap.size();
	}

	/**
	 * Returns a string representation of this data structure. Each component is represented as
	 * {v_i:v_1,v_2,v_3,...v_n}, where v_i is the representative of the set.
	 * 
	 * @return string representation of this data structure
	 */
	public String toString()
	{
		Map<T, Set<T>> setRep = new LinkedHashMap<>();
		for (T t : nodeMap.keySet()) {
			T representative = find(t);
			if (!setRep.containsKey(representative)) {
				setRep.put(representative, new LinkedHashSet<>());
			}
			setRep.get(representative).add(t);
		}

		return setRep
				.keySet().stream()
				.map(
						key -> "{" + key + ":" + setRep.get(key).stream().map(Objects::toString).collect(
								Collectors.joining(",")) + "}")
				.collect(Collectors.joining(", ", "{", "}"));
	}
	
	public Set<T> getGroupOfElement(T element){
		T root = find(element);
		TreeNode<T> rootNode = nodeMap.get(root);
		return rootNode!=null ? rootNode.getAllSubTree() : new HashSet<T>();
	}
	
	public Set<T> deleteGroupofElement(T element) {
		Set<T> set = getGroupOfElement(element);
		for (T t : set) {
			nodeMap.remove(t);
			rankMap.remove(t);
		}
		count--;
		return set;
		
	}

	private boolean isElementIncluded(T element) {
		return nodeMap.containsKey(element);
	}
	

	private T getParentData(T current) {
		TreeNode<T> parent = nodeMap.get(current).getParent();
		return parent!=null ? parent.getData() : current;
	}

	private void setParent(T current, final T root) {
		TreeNode<T> currentNode = nodeMap.get(current);
		TreeNode<T> rootNode = nodeMap.get(root);
		TreeNode<T> parent = currentNode.getParent();
		if(parent!=null) {
			parent.removeChild(currentNode);
		}
		rootNode.addChild(currentNode);
	}

}

//End UnionFind.java
