/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int dimension;
    private int[] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("");
        }
        dimension = tiles.length;
        board = new int[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i * dimension + j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result.append(board[i * dimension + j] + " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingNum = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != (i + 1) && board[i] != 0) {
                hammingNum++;
            }
        }
        return hammingNum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanNum = 0;
        int x_exp, y_exp;
        int x_cur, y_cur;

        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + 1 && board[i] != 0) {
                x_cur = i / dimension;
                y_cur = i % dimension;
                x_exp = (board[i] - 1) / dimension;
                y_exp = (board[i] - 1) % dimension;
                manhattanNum += Math.abs(x_exp - x_cur) + Math.abs(y_exp - y_cur);
            }
        }
        return manhattanNum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (manhattan() == 0 && hamming() == 0) {
            return true;
        }
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < board.length; i++) {
            if (this.board[i] != that.board[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        ArrayList<Integer> list = new ArrayList<>();
        int indexEmptyTile;
        int x, y;

        for (indexEmptyTile = 0; indexEmptyTile < board.length; indexEmptyTile++) {
            if (board[indexEmptyTile] == 0) {
                x = indexEmptyTile / dimension;
                y = indexEmptyTile % dimension;
                // up site
                if (x - 1 >= 0) {
                    list.add((x - 1) * dimension + y);
                }
                // down site
                if (x + 1 <= dimension - 1) {
                    list.add((x + 1) * dimension + y);
                }
                // left site
                if (y - 1 >= 0) {
                    list.add(x * dimension + (y - 1));
                }
                // right site
                if (y + 1 <= dimension - 1) {
                    list.add(x * dimension + y + 1);
                }
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            int[] neighbor = Arrays.copyOf(board, board.length);
            int[][] temp = new int[dimension][dimension];
            int valSwap = neighbor[list.get(i)];
            neighbor[list.get(i)] = 0;
            neighbor[indexEmptyTile] = valSwap;
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    temp[j][k] = neighbor[j * dimension + k];
                }
            }
            queue.enqueue(new Board(temp));
        }

        return queue;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] copy = Arrays.copyOf(board, board.length);
        int[][] temp = new int[dimension][dimension];
        int x, y;

        for (int i = 0; i < board.length; i++) {
            if (copy[i] != 0) {
                x = i / dimension;
                y = i % dimension;
                // up site
                if (x - 1 >= 0) {
                    if (copy[(x - 1) * dimension + y] != 0) {
                        int valSwap = copy[i];
                        copy[i] = copy[(x - 1) * dimension + y];
                        copy[(x - 1) * dimension + y] = valSwap;
                        break;
                    }
                }
                // down site
                if (x + 1 <= dimension - 1) {
                    if (copy[(x + 1) * dimension + y] != 0) {
                        int valSwap = copy[i];
                        copy[i] = copy[(x + 1) * dimension + y];
                        copy[(x + 1) * dimension + y] = valSwap;
                        break;
                    }
                }
                // left site
                if (y - 1 >= 0) {
                    if (copy[x * dimension + (y - 1)] != 0) {
                        int valSwap = copy[i];
                        copy[i] = copy[x * dimension + (y - 1)];
                        copy[x * dimension + (y - 1)] = valSwap;
                        break;
                    }
                }
                // right site
                if (y + 1 <= dimension - 1) {
                    if (copy[x * dimension + y + 1] != 0) {
                        int valSwap = copy[i];
                        copy[i] = copy[x * dimension + y + 1];
                        copy[x * dimension + y + 1] = valSwap;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                temp[i][j] = copy[i * dimension + j];
            }
        }
        return new Board(temp);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println(initial.hamming());
    }
}
