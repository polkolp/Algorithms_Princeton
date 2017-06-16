import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private Item[] randomQ; 
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        randomQ = (Item[]) new Object[4];   
    }
    public boolean isEmpty()                 // is the queue empty?
    { return count == 0; }
    
    public int size()                        // return the number of items on the queue
    { return count; }
    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException();
        if (count == randomQ.length) resize(2*randomQ.length);
        randomQ[count++] = item;
    }
    private void resize(int size)
    {
        Item [] tempQ = (Item[]) new Object[size];
        for (int i = 0; i < count; i++) {
            tempQ[i] = randomQ[i];
        }  
        randomQ = tempQ;
    }
    private void swapLast(int idx) //swap selected item with last one for dequeue. import index.
    {
        Item k = randomQ[idx];
        randomQ[idx] = randomQ[count-1];
        randomQ[count-1] = k;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if (count == 0) throw new NoSuchElementException();
        int n = StdRandom.uniform(count);
        if (n != 1) swapLast(n);
        Item item = randomQ[--count];
        randomQ[count] = null;
        if (count > 0 && count < randomQ.length/4) resize(randomQ.length/2);
        return item;
    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if (count == 0) throw new NoSuchElementException(); 
        int n = StdRandom.uniform(count);
        return randomQ[n];
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    { return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        private int n = count;
        private int[] p = StdRandom.permutation(n);
        public boolean hasNext() { return n > 0; } //backward
        public Item next() {
            if (n == 0) throw new NoSuchElementException();
            return randomQ[p[--n]]; 
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        r.enqueue("a");
        r.enqueue("b");
        r.enqueue("c");
        StdOut.println(r.sample());
        StdOut.println(r.size());
        StdOut.println(r.dequeue());
        StdOut.println(r.dequeue());
        StdOut.println(r.dequeue());
        
        
    }
}