/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean solvable;
    private SearchNode lastSearchNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        // mandatory queue
        boolean solved = false;
        MinPQ<SearchNode> nodes = new MinPQ<>();
        nodes.insert(new SearchNode(initial, 0, null));

        // twin queue
        boolean twinSolved = false;
        MinPQ<SearchNode> twinNodes = new MinPQ<>();
        twinNodes.insert(new SearchNode(initial.twin(), 0, null));

        while (!solved && !twinSolved) {
            // get from priority queue board with minimum of priority
            SearchNode current = nodes.delMin();
            SearchNode predecessor = current.getPredecessor();
            solved = current.getBoard().isGoal();

            // copy neighbors of current board into new queue
            Queue<Board> neighbors = new Queue<>();
            for (Board b : current.getBoard().neighbors()) {
                neighbors.enqueue(b);
            }

            // then add into min priority queue new SearchNodes from queue
            while (neighbors.size() != 0) {
                Board board = neighbors.dequeue();
                if (predecessor != null && predecessor.getBoard().equals(board)) {
                    continue;
                }
                nodes.insert(new SearchNode(board, current.getMoves() + 1, current));
            }

            // extract form priority queue board with minimum of priority
            SearchNode twinCurrent = twinNodes.delMin();
            SearchNode twinPredecessor = twinCurrent.getPredecessor();
            twinSolved = twinCurrent.getBoard().isGoal();

            // copy neighbors of current board into queue
            Queue<Board> twinNeighbors = new Queue<>();
            for (Board b : twinCurrent.getBoard().neighbors()) {
                twinNeighbors.enqueue(b);
            }

            // then add into min priority queue new SearchNodes from queue
            while (twinNeighbors.size() != 0) {
                Board board = twinNeighbors.dequeue();
                if (twinPredecessor != null && twinPredecessor.getBoard().equals(board)) {
                    continue;
                }
                twinNodes.insert(new SearchNode(board, twinCurrent.getMoves() + 1, twinCurrent));
            }

            lastSearchNode = current;
        }
        solvable = !twinSolved;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.isSolvable()) {
            return -1;
        }
        return lastSearchNode.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<>();
        SearchNode lastNode = lastSearchNode;
        if (this.isSolvable()) {
            while (lastNode.getPredecessor() != null) {
                boards.push(lastNode.getBoard());
                lastNode = lastNode.getPredecessor();
            }
            boards.push(lastNode.getBoard());
            return boards;
        }
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode predecessor;
        private int moves;
        private int priority;

        public SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        public int getPriority() {
            return this.priority;
        }

        public int getMoves() {
            return this.moves;
        }

        public Board getBoard() {
            return this.board;
        }

        public SearchNode getPredecessor() {
            return this.predecessor;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
