/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

package week2.StackAndQueues;

import java.util.NoSuchElementException;

public class QueueWithTwoStacks<Item> {
    private Stack<Item>[] stack;

    public QueueWithTwoStacks() {
        stack = new Stack[2];
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        stack[0].push(item);
    }

    public Item dequeue() {
        if (stack[1].isEmpty()) {
            while (!stack[0].isEmpty()) {
                stack[1].push(stack[0].pop());
            }
        }
        return stack[1].pop();
    }

    private class Stack<Item> {
        private Item[] items;
        private int size = 0;

        public Stack() {
            items = (Item[]) new Object[1];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(Item item) {
            if (item == null) {
                throw new IllegalArgumentException("item is null");
            }
            if (size == items.length) {
                resize(items.length * 2);
            }
            items[size++] = item;
        }

        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("no element");
            }
            if (size > 0 && size == items.length / 4) {
                resize(items.length / 2);
            }
            Item item = items[--size];
            items[size] = null;
            return item;
        }

        private void resize(int capacity) {
            Item[] copy = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                copy[i] = items[i];
            }
            items = copy;
        }
    }

}
