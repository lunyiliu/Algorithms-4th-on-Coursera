/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;//num of items
    private int tail;//the rightest index of items
    private Item[] Array;

    private class ArrayIterator implements Iterator<Item> {
        private Item[] IteratorArray;
        private int i;

        public ArrayIterator() {
            IteratorArray = (Item[]) new Object[n];
            int j = 0;
            for (int k = 0; k < Array.length; k++) {
                if (Array[k] != null) {
                    IteratorArray[j++] = Array[k];
                }
            }
            StdRandom.shuffle(IteratorArray);
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i != n;
        }

        @Override
        public Item next() {
            if (IteratorArray[i] == null) {
                throw new NoSuchElementException();
            }
            return IteratorArray[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void AddSize() {
        Item[] NewArray = (Item[]) new Object[n * 2];
        int j = 0;
        for (int i = 0; i < Array.length; i++) {
            if (Array[i] != null) {
                NewArray[j++] = Array[i];
            }
        }
        Array = NewArray;
        tail = j - 1;
    }

    private void HalfSize() {
        Item[] NewArray = (Item[]) new Object[Array.length / 2];
        int j = 0;
        for (int i = 0; i < Array.length; i++) {
            if (Array[i] != null) {
                NewArray[j++] = Array[i];
            }
        }
        Array = NewArray;
        tail = j - 1;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        Array = (Item[]) new Object[1];
        n = 0;
        tail = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (tail >= Array.length - 1) AddSize();
        Array[++tail] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException();
        if (n * 4 <= Array.length) HalfSize();
        //StdRandom.shuffle(Array);
        int RandomIndex = StdRandom.uniform(tail + 1);
        while (Array[RandomIndex] == null) {
            RandomIndex = StdRandom.uniform(tail + 1);
        }
        Item item = Array[RandomIndex];
        Array[RandomIndex] = null;
        n--;
        if (RandomIndex == tail) tail--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException();
        if (n * 4 <= Array.length) HalfSize();
        int RandomIndex = StdRandom.uniform(tail + 1);
        while (Array[RandomIndex] == null) {
            RandomIndex = StdRandom.uniform(tail + 1);
        }
        return Array[RandomIndex];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> MyRQ = new RandomizedQueue<String>();
        MyRQ.enqueue("a");
        MyRQ.enqueue("b");
        MyRQ.enqueue("c");
        MyRQ.enqueue("d");
        MyRQ.enqueue("e");
        for (String s : MyRQ) {
            StdOut.print(s);
            StdOut.print(" ");
        }
        StdOut.println();
        StdOut.print(MyRQ.dequeue());
        StdOut.println();
        StdOut.print(MyRQ.dequeue());
        StdOut.println();
        StdOut.print(MyRQ.dequeue());
        StdOut.println();
        StdOut.print(MyRQ.dequeue());
        StdOut.println();
        StdOut.print(MyRQ.dequeue());
        for (String s : MyRQ) {
            StdOut.print(s);
            StdOut.print(" ");
        }
        StdOut.println();
        MyRQ.enqueue("a");
        for (String s : MyRQ) {
            StdOut.print(s);
            StdOut.print(" ");
        }
    }
}

