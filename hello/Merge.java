/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Merge {
    private static Comparable[] aux;

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                aux[k] = a[j++];
            }
            else if (j > hi) {
                aux[k] = a[i++];
            }
            else if (less(aux[j], aux[i])) {
                aux[k] = a[j++];
            }
            else {
                aux[k] = a[i++];
            }
        }

        assert isSorted(a, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        if (!less(a[mid + 1], a[mid])) {
            return;
        }
        merge(a, aux, lo, mid, hi);
    }

    private static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        for (int k = 0; k < a.length; k++) {
            aux[k] = a[k];
        }
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
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
        /*
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
            if (i % 50 == 0) {
                StdOut.println();
            }
        }
         */
    }
}
