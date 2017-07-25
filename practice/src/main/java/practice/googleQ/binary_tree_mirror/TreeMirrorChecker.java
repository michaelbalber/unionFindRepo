package practice.googleQ.binary_tree_mirror;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public class TreeMirrorChecker {
	
	public static boolean checkMirror(BinaryNode root) {
	
		ArrayList<Integer> treeArray = treeToArray(root);
		//2^(depth+1)-1 = size
		int size = treeArray.size();
		double depth = Math.log(size+1)/Math.log(2)-1; 
		for(int i=1; i<depth ; i++) {
			for(int j=0; j<Math.pow(2, i-1);j++) {
				if(treeArray.get((int) (Math.pow(2, depth)-1+j)) != 
						treeArray.get((int) (Math.pow(2, depth+1)-2-j))) {
					return false;
				}
			}
		}
		
		return true;
	}

	static ArrayList<Integer> treeToArray(BinaryNode root) {
		
		LinkedList<BinaryNode> nodeQ = new LinkedList<>();
		LinkedList<Integer> allValues = new LinkedList<>();
		nodeQ.add(root);
		while(!nodeQ.isEmpty()) {
			BinaryNode node = nodeQ.removeFirst();
			allValues.add(node.getValue());
			Optional.ofNullable(node.getLeftChild()).
				ifPresent(x->nodeQ.add(x));
			Optional.ofNullable(node.getRightChild()).
				ifPresent(x->nodeQ.add(x));
			
		}
		return new ArrayList<Integer>(allValues);
	}
}
