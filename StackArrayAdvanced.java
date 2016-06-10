//with resizing and iteration
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class StackArrayAdvanced<Item> implements Iterable<Item>{
	private Item [] stack = (Item[]) new Object[1];
	private int N;
	public void resize(int max) {
		Item[] newstack= (Item[]) new Object[max];
		for (int i = 0; i< N; i++){
			newstack[i] = stack[i];
		}
		stack = newstack;
	}
	
	public void push(Item item) {
		if (N == stack.length) resize(2*stack.length);
		stack[N++] = item;
	}
	public Item pop() {
		if (N > 0 && N <= stack.length/4) resize(stack.length/2);
		Item item = stack[--N];
		stack[N] = null;
		return item;
	}
	public boolean isEmpty() {
		return N==0;
	}
	public int size() {
		return N;
	}
	public Iterator<Item> iterator() {
		return new arrayiterator();
	}
	private class arrayiterator implements Iterator<Item> {
		private int i = N;
		public boolean hasNext() {
			return i>0;
		}
		public Item next() {
			return stack[--i];
		}
		public void remove() {
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StackArrayAdvanced<String> fs = new StackArrayAdvanced<String>();
		while (!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")){
				fs.push(s);
			}
			else {
				StdOut.println(fs.pop() + "\t");
			}		
		}
	}

}
