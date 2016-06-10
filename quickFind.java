public class quickFind {
	private int[] id;
	private int count;
	public quickFind(int N){
		count = N;
		id = new int[N];
		for (int i = 0; i< id.length; i++){
			id[i] = i;
		}
	}
	public boolean connected(int p, int q){
		return (Find(p) == Find(q));
	}
	private int Find(int p){
		return id[p];
	}
	public void union(int p, int q){
		int pid = id[p];
		int qid = id[q];
		if (pid == qid){ return; };
		for (int i =0; i < id.length; i++){
			if (id[i] == pid){
				id[i] = qid;
			}
		}
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
		String unionCommand = "7-3 6-4 9-6 9-3 0-6 4-1";

		quickFind uf = new quickFind(10);
		uf.unionOnCommand(unionCommand);
		uf.idprint();
	}

}
