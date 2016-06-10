import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StackFixedCapacity<Item> {
	private Item [] stack;
	private int N;
	public StackFixedCapacity(int size){
		stack = (Item[]) new Object[size];
	}
	
	public void push(Item item) {
		stack[N++] = item;
	}
	public Item pop() {
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StackFixedCapacity<String> fs = new StackFixedCapacity<String>(100);
		while (!StdIn.isEmpty()){
			String s = StdIn.readString();
			if (!s.equals("-")){
				fs.push(s);
			}
			else {
				StdOut.println(fs.pop());
			}		
		}
	}

}
