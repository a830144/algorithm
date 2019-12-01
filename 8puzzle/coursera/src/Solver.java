import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	// for solve
	private MinPQ<Board> minPQ = new MinPQ<Board>(new SortByPriority());
	// for final result
	private MinPQ<Board> minPQIter = new MinPQ<Board>(new SortByPriority());
	// for isSolve
	private MinPQ<Board> minPQIsSolved = new MinPQ<Board>(new SortByPriority());

	private int min = -1;

	private Board initialBoard;

	private int[][] neighborBlocks;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		this.neighborBlocks = new int[initial.dimension()][initial.dimension()];

		int initHamming = initial.hamming();
		int initmanhattan = initial.manhattan();
		this.initialBoard = initial;
		System.out.println("initHamming--" + initHamming + ";initmanhattan--" + initmanhattan);
		if (isSolvable()) {

			minPQ.insert(initial);
			Board searchNode = initial;
			while (!minPQ.min().isGoal()) {
				Board delMin = minPQ.delMin();
				minPQIter.insert(delMin);
				/*
				 * System.out.println("next:: moves--" + delMin.getMoves() +
				 * ";manhattan()--" + delMin.manhattan() + "=" +
				 * (delMin.getMoves() + delMin.manhattan()));
				 * System.out.println(delMin);
				 */
				/*if (moves() != 0) {
					delMin.setPredecessor(searchNode);
				}*/
				searchNode = delMin;

				Iterable<Board> minPQNeighbors = searchNode.neighbors();
				Iterator<Board> iterator = minPQNeighbors.iterator();
				while (iterator.hasNext()) {
					Board boardtemp = iterator.next();
					boardtemp.hamming();
					boardtemp.manhattan();
					minPQ.insert(boardtemp);
				}
				this.min++;
			}
			/* System.out.println("FINAL::" + minPQ.min()); */
			minPQIter.insert(minPQ.min());
			
		}
	}

	private void copyNeighbors(int[][] template, int dimension) {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				neighborBlocks[i][j] = template[i][j];
			}
		}
	}

	private void exec(int[][] arr, int a, int b, int c, int d) {
		int temp = arr[a][b];
		arr[a][b] = arr[c][d];
		arr[c][d] = temp;
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		boolean result = true;

		int dimension = initialBoard.dimension();
		int[][] template = new int[dimension][dimension];
		for (int number = 1; number < (dimension * dimension); number++) {
			int supposeRow = (number % dimension) == 0 ? (number / dimension) - 1 : number / dimension;
			int supposeCol = (number % dimension == 0 ? dimension - 1 : number % dimension - 1);
			template[supposeRow][supposeCol] = number;
		}
		template[dimension - 1][dimension - 1] = 0;

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				Board board;
				if (i != 0) {
					copyNeighbors(template, dimension);
					exec(neighborBlocks, i, j, i - 1, j);

					board = new Board(neighborBlocks);
					minPQIsSolved.insert(board);
				}
				if (i != dimension - 1) {
					copyNeighbors(template, dimension);
					exec(neighborBlocks, i, j, i + 1, j);

					board = new Board(neighborBlocks);
					minPQIsSolved.insert(board);
				}
				if (j != 0) {
					copyNeighbors(template, dimension);
					exec(neighborBlocks, i, j, i, j - 1);

					board = new Board(neighborBlocks);
					minPQIsSolved.insert(board);
				}
				if (j != dimension - 1) {
					copyNeighbors(template, dimension);
					exec(neighborBlocks, i, j, i, j + 1);

					board = new Board(neighborBlocks);
					minPQIsSolved.insert(board);
				}
			}

		}
		Iterator<Board> iter = minPQIsSolved.iterator();
		while (iter.hasNext()) {
			Board board = iter.next();
			if (board.equals(initialBoard)) {
				result = false;
				break;
			}
		}

		if (result) {
			System.out.println("solve!!");
		} else {
			System.out.println("unsolve!!");
		}

		return result;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if (!isSolvable()) {
			return -1;
		} else {
			return this.min;
		}

	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (!isSolvable()) {
			return null;
		} else {
			return this.minPQIter;
		}
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
		/*
		 * int[][] blocks = new int[3][3]; blocks[0][0] = 0; blocks[0][1] = 1;
		 * blocks[0][2] = 3;
		 * 
		 * blocks[1][0] = 4; blocks[1][1] = 2; blocks[1][2] = 5;
		 * 
		 * blocks[2][0] = 7; blocks[2][1] = 8; blocks[2][2] = 6; Board initial =
		 * new Board(blocks); Solver solver = new Solver(initial);
		 */

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

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
	private class SortByPriority implements Comparator<Board>
    {
        public int compare(Board a, Board b)
        {
        	if (a.manhattan()  < b.manhattan() ) {
    			return -1;
    		} else {
    			return 1;
    		}
        	
        }
    }
	
}