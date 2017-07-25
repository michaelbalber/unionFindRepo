package practice.googleQ.binary_tree_mirror;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TreeMirrorCheckerTest {

	@Test
	public void testTreeToArray() {
		BinaryNode root = new BinaryNode(null, 7);
		BinaryNode left1 = new BinaryNode(root, 6);
		BinaryNode right11 = new BinaryNode(root, 15);
		root.setLeftChild(left1);
		root.setRightChild(right11);
		ArrayList<Integer> res = TreeMirrorChecker.treeToArray(root );
		assertEquals(3, res.size());
		System.out.println(res);
	}
	
	@Test
	public void testCheckMirror() {
		BinaryNode root = new BinaryNode(null, 70);
		BinaryNode left1 = new BinaryNode(root, 60);
		BinaryNode right1 = new BinaryNode(root, 60);
		root.setLeftChild(left1);
		root.setRightChild(right1);
		
		BinaryNode left2 = new BinaryNode(left1, 40);
		BinaryNode right2 = new BinaryNode(left1, 50);
		left1.setLeftChild(left2);
		left1.setRightChild(right2);
		
		BinaryNode left3 = new BinaryNode(right1, 50);
		BinaryNode right3 = new BinaryNode(right1, 40);
		right1.setLeftChild(left3);
		right1.setRightChild(right3);
		assertTrue(TreeMirrorChecker.checkMirror(root));
	
		
		
	}

}
