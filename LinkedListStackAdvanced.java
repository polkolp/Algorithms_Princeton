import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class LinkedListStackAdvanced<Item> implements Iterable<Item>{
	private Node first;
	private int N;
	private class Node{
		Item item;
		Node next;
	}
	public void push(Item input) {
		Node oldfirst = first;
		first = new Node();
		first.item = input;
		first.next = oldfirst;
		N++;
	}
	public Item pop() {
		Item item = first.item;
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
	public Iterator<Item> iterator(){
		return new Listiterator();
	}
	private class Listiterator implements Iterator<Item> {
		private Node current = first;
		public boolean hasNext() {
			return (current != null);
		}
		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}
		public void remove() {
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedListStackAdvanced<String> ls = new LinkedListStackAdvanced<String>();
		while (!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")){
				ls.push(s);
			}
			else if (!ls.isEmpty()){
				StdOut.print(ls.pop() + "\n");
			}
			StdOut.println(ls.size() + " left on stack");
		}
	}

}
