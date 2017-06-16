import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
//    private Node temp;
    private int n;
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() { }                          // construct an empty deque
    
    public boolean isEmpty()                 // is the deque empty?
    { return n == 0; }
    public int size()                        // return the number of items on the deque
    { return n; }
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new NullPointerException();
        if (n == 0) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.prev = null;
            oldfirst.prev = first; 
        }
        n++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new NullPointerException();
        if (n == 0) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node(); 
            last.item = item;
            last.next = null;
            last.prev = oldlast;
            oldlast.next = last;
        }
        n++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (n == 1) {
            last = null; // check pointer here
        }
        else {
            first.next.prev = null;
        }
        first = first.next;
        n--;
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (n == 1) {
            first = null;
        }
        else {
            last.prev.next = null;
        }
        last = last.prev;
        n--;
        return item;
        
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    { return new ListIterator(); }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext()
        { return current != null; }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> d = new Deque<String>();
//        d.addFirst("a");
//        d.addFirst("b");
//        StdOut.println(d.removeFirst());
//        StdOut.println(d.removeFirst());
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            d.addFirst(item);
            StdOut.println(d.removeLast());
        }
        
        StdOut.println(d.isEmpty());
        StdOut.println(d.size());
    }
    
    
    
    
}