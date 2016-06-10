import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class LinkedListStack {
	private Node first;
	private int N;
	private class Node{
		String item;
		Node next;
	}
	public void push(String input) {
		Node oldfirst = first;
		first = new Node();
		first.item = input;
		first.next = oldfirst;
		N++;
	}
	public String pop() {
		String item = first.item;
		first = first.next;
		N--;
		return item;
	}
	public boolean isEmpty() {
		return first == null;
	}
	public int size() {
		return N;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedListStack ls = new LinkedListStack();
		while (!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")){
				ls.push(s);
			}
			else if (!ls.isEmpty()){
				StdOut.print(ls.pop() + "\n");
			}
			StdOut.println(ls.size() + "left on stack");
		}
	}

}
