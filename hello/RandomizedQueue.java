import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element");
        }
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        int random = StdRandom.uniformInt(size);
        Item item = items[random];
        size--;

        if (random == size) {
            items[random] = null;
            return item;
        }

        items[random] = items[size];
        items[size] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element");
        }
        return items[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int i = size;

            public boolean hasNext() {
                return i > 0;
            }

            public void remove() { /* not supported */
                throw new UnsupportedOperationException("not supported");
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("no element in randomized queue");
                }

                if (i == 1) {
                    i = 0;
                    return items[0];
                }

                int random = StdRandom.uniformInt(i);
                Item result = items[random];
                i--;

                if (random == i) {
                    return result;
                }

                Item copyItem = items[random];
                items[random] = items[i];
                items[i] = copyItem;
                return result;
            }
        };
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("first");
        queue.enqueue("second");
        queue.enqueue("third");
        queue.enqueue("forth");
        queue.enqueue("five");
        queue.enqueue("six");
        queue.enqueue("seven");

        for (String s : queue) {
            StdOut.println(s);
        }

        StdOut.println();
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());

        StdOut.println();
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
    }
}
