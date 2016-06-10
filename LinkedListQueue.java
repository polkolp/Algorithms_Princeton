import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class LinkedListQueue<Item> implements Iterable<Item>{
	private Node first;
	private Node last;
	private int N;
	private class Node {
		Item item;
		Node next;
	}
	public void enqueue(Item item){
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()){
			first = last;
		}
		else {
			oldlast.next = last;
		}
		N++;
	}
	public Item dequeue() {
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) last = null; 
		return item;
	}
	public boolean isEmpty() {
		return N==0;
	}
	public int size() {
		return N;
	}
	public Iterator<Item> iterator() {
		return new listiterator();
	}
	private class listiterator implements Iterator<Item> {
	private Node current = first;
	public boolean hasNext() {
		return current != null;
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
		LinkedListQueue<String> lq= new LinkedListQueue<String >();
		while (!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")){
				lq.enqueue(s);
			}
			else{
				StdOut.println(lq.dequeue() + "\n");
			}
		}
	}

}
