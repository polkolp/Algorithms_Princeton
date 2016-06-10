import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class QueueArrayAdvanced<Item> implements Iterable<Item>{
	private Item[] queue= (Item[]) new Object[1];
	private int N;
	private int dq;
	public void resize(int max) {
		Item[] newqueue = (Item[]) new Object[max];
		for (int i = dq; i < N; i++){
			newqueue[i-dq] = queue[i];
		}
		dq = 0;
		N = N-dq;
		queue = newqueue;
	}
	public void enqueue(Item item) {
		if (N == queue.length) resize(2*queue.length);
		queue[N++] = item;
/*		for (int i =0; i<N; i++){
		StdOut.print(queue[i] + "\t");
		}*/
	}
	public Item dequeue() {

		Item it = queue[dq];
		queue[dq++] = null;
		if (size() <= queue.length/4) resize(queue.length/2);
/*		for (int i =0; i<N; i++){
		StdOut.print(queue[i] + "\t");
		}*/
		return it;
	}
	public boolean isEmpty() {
		return size() == 0;
	}
	public int size() {
		return N-dq+1;
	}
	public Iterator<Item> iterator() {
		return new arrayiterator();
	}
	private class arrayiterator implements Iterator<Item>{
		public boolean hasNext() {
			return size() > 0;
		}
		public Item next() {
			return queue[dq++];
		}
		public void remove() {
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QueueArrayAdvanced<String> qa = new QueueArrayAdvanced<String>();
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")) qa.enqueue(s);
			else StdOut.println(qa.dequeue() + "\n");
		}
	}

}
