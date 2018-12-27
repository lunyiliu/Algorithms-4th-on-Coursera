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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class ListIterator implements Iterator<Item> {
        private Node current = FirstNode;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (current != null) {
                Item item = current.item;
                current = current.next;
                return item;
            }
            else
                throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node last;

        public Node(Item item_, Node next_, Node last_) {
            item = item_;
            next = next_;
            last = last_;
        }
    }


    private Node FirstNode;
    private Node LastButOneNode;// the second Node from last
    private int n;// nums of nodes in the dequeue

    // construct an empty deque
    public Deque() {
        n = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            FirstNode = new Node(item, null, null);
            LastButOneNode = new Node(null, null, null);
            n++;
        }
        else {
            Node OldFirst = FirstNode;
            FirstNode = new Node(item, OldFirst, null);
            FirstNode.next.last = FirstNode;
            n++;
            if (n == 2) {
                LastButOneNode = FirstNode;
            }
        }

    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            FirstNode = new Node(item, null, null);
            LastButOneNode = new Node(null, null, null);
            n++;

        }
        else {
            if (n == 1) {
                LastButOneNode = FirstNode;
                LastButOneNode.next = new Node(item, null, LastButOneNode);
                FirstNode = LastButOneNode;
                n++;

            }
            else {
                LastButOneNode = LastButOneNode.next;
                LastButOneNode.next = new Node(item, null, LastButOneNode);
                n++;
            }
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException();
        if (n == 1) {
            LastButOneNode = null;
        }
        Item item = FirstNode.item;
        FirstNode = FirstNode.next;//don't know if the memebers of a null object can be cited
        if (n != 1) {
            FirstNode.last = null;
            n--;
            return item;
        }
        else {
            n--;
            return item;
        }

    }

    // remove and return the item from the end
    public Item removeLast() {
        if (n == 0) throw new NoSuchElementException();
        if (n > 2) {
            LastButOneNode.next.last = null;
            Item item = LastButOneNode.next.item;
            LastButOneNode.next = null;
            LastButOneNode = LastButOneNode.last;
            n--;
            return item;
        }
        else {
            if (n == 2) {
                Item item = LastButOneNode.next.item;
                LastButOneNode.next.last = null;
                LastButOneNode.next = null;
                LastButOneNode = null;
                n--;
                return item;
            }
            else {
                Item item = FirstNode.item;
                FirstNode = null;
                n--;
                return item;
            }
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> MyDequeue = new Deque<String>();
        MyDequeue.addFirst("A");
        MyDequeue.addFirst("B");
        MyDequeue.addFirst("C");
        MyDequeue.addFirst("D");
        MyDequeue.addFirst("E");
        for (String s : MyDequeue)
            StdOut.print(s);
        StdOut.println();
        MyDequeue.removeFirst();
        MyDequeue.removeFirst();
        for (String s : MyDequeue)
            StdOut.print(s);

        MyDequeue.removeLast();
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);

        MyDequeue.addLast("A");
        //StdOut.println(MyDequeue.LastButOneNode.item);
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);

        MyDequeue.removeFirst();
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);
        MyDequeue.addFirst("C");
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);
        MyDequeue.addLast("E");
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);
        // StdOut.println();
        //StdOut.println(MyDequeue.LastButOneNode.item);
        // StdOut.println(MyDequeue.LastButOneNode.next.item);
        //StdOut.println(MyDequeue.LastButOneNode.next.last.item);

        MyDequeue.removeLast();

        //StdOut.println(MyDequeue.LastButOneNode.item);
        MyDequeue.removeLast();
        MyDequeue.removeLast();
        MyDequeue.removeLast();
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);
        MyDequeue.addFirst("E");
        MyDequeue.addFirst("D");
        for (String s : MyDequeue)
            StdOut.print(s);
        MyDequeue.removeFirst();
        StdOut.println();
        for (String s : MyDequeue)
            StdOut.print(s);
        MyDequeue.removeLast();
        for (String s : MyDequeue)
            StdOut.print(s);
    }
}
