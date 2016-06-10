import edu.princeton.cs.algs4.*;
public class quickUnion {
	private int[] id;
	private int count;

	public quickUnion(int N){
		count = N;
		id = new int[N];
		for (int i = 0; i< id.length; i++){
			id[i] = i;
		}
	}

	public boolean connected(int p, int q) {
		if (root(p) == root(q)){
			return true;
		}
		return false;
	}
	private int root(int p) {
		int q = p;
		while (id[p] != p){
/*			id[p] = id[id[p]]; // a way of path contraction by associating object to grandparent directly*/
			p = id[p];
		}
//		id[q] = p; //My way: associate object directly to Adam.
		return p;
	}
	public void union(int p, int q) {
		int proot = root(p);
		int qroot = root(q);
		if (proot == qroot) return;
		id[proot] = qroot;
		count--;	
	}
	private void unionOnCommand(String input){
		int p, q;
		for (String s: input.split(" ")){
			char a = s.charAt(0);
			char b = s.charAt(2);
			p = Character.getNumericValue(a);
			q = Character.getNumericValue(b);
			union(p, q);
		}
	}
	private void idprint() {
		for (int i = 0; i<id.length; i++){
			System.out.print(id[i] + " ");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String unionCommand = "7-9 1-5 8-2 2-7 1-3 0-3 4-3 8-1 5-6";
		quickUnion qu = new quickUnion(10);
		qu.unionOnCommand(unionCommand);
		qu.idprint();
	}

}
