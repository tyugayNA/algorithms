/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        String name1 = args[0];
        String name2 = args[1];
        StdOut.println("Hello " + name1 + " and " + name2);
        StdOut.println("Goodbye " + name2 + " and " + name1);
    }
}
