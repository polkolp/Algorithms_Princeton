import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> 
{
    private Node root; // root of current BST
    private class Node {
        private final Key key;
        private Value value;
        private int size;
        private Node left, right;
        
        public Node(Key key, Value value, int N) { // constructor of the Node
            this.key = key;
            this.value = value;
            this.size = N;
        }
    }   
    public BinarySearchTree() { }
    public void put(Key key, Value value) { // put a value into BST
        root = put(root, key, value); // update the counts and return a clean root
    }
    private Node put(Node x, Key key, Value value) { // NOTICE RETURN TYPE IS NODE
        if (x == null) return new Node(key, value, 1); // if recursion gives no results add a new node. pointer left and right are automatically null
        int cmp = key.compareTo(x.key); // avoid repetitive computation
        if (cmp < 0) x.left = put(x.left, key, value); // cannot use < or > directly
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.value = value; // otherwise update value
        // update size. Two cases: a new Node or updating an old node.
        x.size = size(x.left) + size(x.right) +1; // try root.left.size later
        return x; // dont forget to return
    }
    
    public Value get(Key key) { // get a value using a key. A recursive implementation requires a nested class
        return get(root, key);
    }
    private Value get(Node x, Key key) { // same logic as put
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(root.right, key);
        else return x.value; 
    }   
    
    public int size() { return size(root); } 
    private int size(Node root) { // this has uses for multiple roots. so don't just return the uppermost root.
        if (root == null) return 0;
        else return root.size;
    }
    public Value min() {
        return min(root).value;
    } 
    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }
    public Value max() { // max of this tree
        return max(root).value;
    }
    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }
    public Key floor(Key k) { // largest element less than or equals k
        Node x = floor(root, k);
        if (x == null) return null;
        return x.key;
    }
    private Node floor(Node x, Key k) { // better return node
        if (x == null) return null;
        int cmp = k.compareTo(x.key); 
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, k); // if <, definitely on the left.
        Node t  = floor(x.right, k);
        if (t != null) return t; // judging if it worth to go to further steps. Notice the return in this branch cannot be null. so if (cmp > 0) return floor(x.left, k) doesn't work directly;
        else return x;
    }
    public Key ceiling(Key k) { // smallest element greater than or equals k
        Node x  = ceiling(root, k);
        if (x == null) return null;
        return x.key;
    }
    private Node ceiling(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp > 0) return ceiling (x.right, k); // if > 0 definitely on the right
        if (cmp == 0) return x;
        Node t = ceiling(x.left, k);
        if (t != null) return t;
        else return x;
    }
    public int rank(Key key) { // rank a key. a rank of k means exactly k elements smaller.
        return rank(root, key);
    }
    private int rank(Node x, Key key) {
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return rank(x.right, key) + size(x.left) + 1; //don't forget +1 for the root
        else return size(x.left);
    }
    public Key select(int n) {
        return select(root, n).key;
    }
    private Node select(Node x, int n) {
        if (x == null) return null;
        int t = size(x.left);
        if (n < t) return select(x.left, n);
        else if (n == t) return x;
        else return select(x.right, n-size(x.left)-1);
    }
    public void deleteMin() { // need some more investigation
        root = deleteMin(root); // why?
    }
    private Node deleteMin(Node x) { // how do I deal with pointers?
        if (x.left == null) return x.right; //ok, this returns to x.left !!!!!!!!
        x.left = deleteMin(x.left); // an invisible else
        x.size = size(x.left) + size(x.right) + 1;
        return x; // and this return recount elements
    }
    public void deleteMax() {
        root = deleteMax(root);
    }
    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    public void delete(Key k) {
        root = delete(root, k);
    }
    private Node delete(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, k);
        else if (cmp > 0) x.right = delete(x.right, k);
        else {
            if (x.right == null) return x.left; // if single node, just implement deleteMin/deleteMax
            if (x.left == null) return x.right;
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    public boolean contains(Key key) {
        return get(key) != null;
    }
    public static void main(String[] args) 
    {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>();
        
        bst.put("S", 0);
        bst.put("K", 1);
        bst.put("T", 2);
        StdOut.println(bst.contains("K"));
        StdOut.println(bst.contains("Q"));
        bst.delete("K");
        
        /*        for (int i = 0; !StdIn.isEmpty(); i++) {
         String key = StdIn.readString();
         bst.put(key, i);
         }
         StdOut.println("S " + bst.get("S")); */
    }
}