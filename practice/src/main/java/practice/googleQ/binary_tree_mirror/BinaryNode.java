package practice.googleQ.binary_tree_mirror;

public class BinaryNode {

	private BinaryNode parent= null;
	private BinaryNode leftChild = null;
	private BinaryNode rightChild = null;
	private int value;
	
	public BinaryNode(BinaryNode parent, int val) {
		this.parent = parent;
		this.value = val;
	}
	
	public BinaryNode getParent() {
		return parent;
	}
	public void setParent(BinaryNode parent) {
		this.parent = parent;
	}
	public BinaryNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(BinaryNode leftChild) {
		this.leftChild = leftChild;
	}
	public BinaryNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(BinaryNode rightChild) {
		this.rightChild = rightChild;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	
	
}
