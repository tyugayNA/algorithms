/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node node = new Node();
        node.item = item;
        size++;

        if (isEmpty()) {
            first = node;
            last = node;
            return;
        }

        node.next = first;
        first.prev = node;
        first = node;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node node = new Node();
        node.item = item;
        size++;

        if (isEmpty()) {
            first = node;
            last = node;
            return;
        }

        node.prev = last;
        last.next = node;
        last = node;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("no element for remove");
        }

        Item item = first.item;

        first = first.next;
        first.prev = null;
        size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("no element for remove");
        }

        Item item = last.item;

        last = last.prev;
        last.next = null;
        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() { /* not supported */
            throw new UnsupportedOperationException("not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no element in deque");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deq = new Deque<>();
        deq.addFirst("first");
        deq.addFirst("second");
        deq.addFirst("third");
        deq.addLast("forth");
        deq.addLast("five");
        deq.addLast("six");

        for (String s : deq) {
            StdOut.println(s);
        }

        StdOut.println(deq.removeFirst());
        StdOut.println(deq.removeLast());
    }
}
