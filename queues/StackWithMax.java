/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

package week2.StackAndQueues;

public class StackWithMax {
    private Double[] items;
    private Double[] maxItems;
    private int size = 0;
    private int sizeMaxItems = 0;

    public StackWithMax() {
        items = new Double[1];
        maxItems = new Double[1];
    }

    public void push(Double item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;

        if (sizeMaxItems == 0 || maxItems[sizeMaxItems].compareTo(item) > 0) {
            maxItems[++sizeMaxItems] = item;
        }
    }

    public Double pop() {
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        Double item = items[--size];
        items[size] = null;

        if (item.compareTo(maxItems[sizeMaxItems - 1]) == 0) {
            if (sizeMaxItems > 0 && sizeMaxItems == maxItems.length / 4) {
                resize(maxItems.length / 2);
            }
            maxItems[sizeMaxItems - 1] = null;
            sizeMaxItems--;
        }

        return item;
    }

    private void resize(int capacity) {
        Double[] copy = new Double[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

}
