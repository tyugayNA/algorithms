/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        while (N > 1) {
            exch(a, 1, N);
            sink(a, 1, --N);
        }
    }

    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && less(a, j, j + 1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        if (a == null) {
            throw new IllegalArgumentException();
        }
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = swap;
    }

    public static void main(String[] args) {
        StdOut.println("Insert N");
        int N = StdIn.readInt();
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniformInt(N - i);
        }
        sort(a);
        StdOut.println("done");

        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
            if (i % 50 == 0) {
                StdOut.println();
            }
        }
    }
}
