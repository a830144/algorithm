
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Board  {
	private final int[][] blocks;
	private int[][] neighborBlocks;
	private Board predecessor;
	
	private Node<Board> first;

	/**
	 * Pointer to last node. Invariant: (first == null && last == null) ||
	 * (last.next == null && last.item != null)
	 */
	private Node<Board> last;
	

	private void setPredecessor(Board predecessor) {
		this.predecessor = predecessor;
	}

	private int hamming = -1;
	private int manhattan = -1;

	private int blank_row = -1;
	private int blank_col = -1;

	//private int moves = 0;

	private Queue queue;

	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		this.blocks = new int[blocks.length][blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
		this.neighborBlocks = new int[blocks.length][blocks.length];
		this.queue = new Queue();
	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		// board dimension n
		return blocks.length;
	}

	public int hamming() {
		// number of blocks out of place
		if(this.hamming!= -1){return this.hamming;}
		int hamming = 0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				int number = blocks[i][j];
				int supposeRow = (number % blocks.length) == 0 ? (number / blocks.length) - 1 : number / blocks.length;
				int supposeCol = (number % blocks.length == 0 ? blocks.length - 1 : number % blocks.length - 1);
				if ((i != supposeRow || j != supposeCol) && number != 0) {
					hamming++;
				}
				if (number == 0) {
					this.blank_row = i;
					this.blank_col = j;
				}
			}
		}
		this.hamming=hamming;
		return hamming;
	}

	/*public void setMoves(int moves) {
		this.moves = moves;
	}*/

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		if(this.manhattan!= -1){return this.manhattan;}
		int manhattan = 0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				int number = blocks[i][j];
				int supposeRow = (number % blocks.length) == 0 ? (number / blocks.length) - 1 : number / blocks.length;
				int supposeCol = (number % blocks.length == 0 ? blocks.length - 1 : number % blocks.length - 1);
				int distance = number != 0 ? (Math.abs(i - supposeRow) + Math.abs(j - supposeCol)) : 0;
				manhattan += distance;
				if (number == 0) {
					this.blank_row = i;
					this.blank_col = j;
				}
			}
		}
		this.manhattan=manhattan;
		return manhattan;
	}

	public boolean isGoal() {
		// is this board the goal board?
		int hamming = this.hamming == -1 ? hamming() : this.hamming;
		int manhattan = this.manhattan == -1 ? manhattan() : this.manhattan;
		return hamming == 00 && manhattan == 0;
	}

	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		return new Board(neighborBlocks);
	}

	public boolean equals(Object y) {

		return this.toString().equals(y.toString());
	}

	private void copyNeighbors() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				neighborBlocks[i][j] = blocks[i][j];
			}
		}
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		if (blank_row == -1) {
			hamming();
		}
		Board board;
		if (blank_row != 0) {
			copyNeighbors();
			neighborBlocks[blank_row][blank_col] = neighborBlocks[blank_row - 1][blank_col];
			neighborBlocks[blank_row - 1][blank_col] = 0;
			board = new Board(neighborBlocks);
			board.setPredecessor(this);
			//board.setMoves(this.moves+1);
			//System.out.println("blank_row != 0::"+board.toString());
			if(predecessor==null || !predecessor.equals(board))queue.insert(board);
		}
		if (blank_row != dimension()-1) {
			copyNeighbors();
			neighborBlocks[blank_row][blank_col] = neighborBlocks[blank_row + 1][blank_col];
			neighborBlocks[blank_row + 1][blank_col] = 0;
			board = new Board(neighborBlocks);
			board.setPredecessor(this);
			//board.setMoves(this.moves+1);
			//System.out.println("blank_row != dimension()::"+board.toString());
			if(predecessor==null || !predecessor.equals(board)){
				queue.insert(board);

			}
		}
		if (blank_col != 0) {
			copyNeighbors();
			neighborBlocks[blank_row][blank_col] =  neighborBlocks[blank_row][blank_col - 1];
			neighborBlocks[blank_row][blank_col - 1] = 0;
			board = new Board(neighborBlocks);
			board.setPredecessor(this);
			//board.setMoves(this.moves+1);
			//System.out.println("blank_col != 0::"+board.toString());
			if(predecessor==null || !predecessor.equals(board))queue.insert(board);
		}
		if (blank_col != dimension()-1) {
			copyNeighbors();
			neighborBlocks[blank_row][blank_col] =  neighborBlocks[blank_row][blank_col + 1];
			neighborBlocks[blank_row][blank_col + 1] = 0;
			board = new Board(neighborBlocks);
			board.setPredecessor(this);
			//board.setMoves(this.moves+1);
			//System.out.println("blank_col != dimension()::"+board.toString());
			if(predecessor==null || !predecessor.equals(board))queue.insert(board);
		}

		return queue;
	}

	public String toString() {
		// string representation of this board (in the output format specified
		// below)
		String str = "";
		str += dimension();
		str += "\n";
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				str += " " + blocks[i][j];
			}
			str += "\n";
		}
		return str;
	}

	public static void main(String[] args) {
		// unit tests (not graded)
		int[][] blocks = new int[3][3];
		blocks[0][0] = 8;
		blocks[0][1] = 1;
		blocks[0][2] = 3;

		blocks[1][0] = 4;
		blocks[1][1] = 2;
		blocks[1][2] = 0;

		blocks[2][0] = 7;
		blocks[2][1] = 6;
		blocks[2][2] = 5;
		Board board = new Board(blocks);
		/*
		 * for (int i = 0; i < blocks.length; i++) { for (int j = 0; j <
		 * blocks[i].length; j++) { System.out.println("[i][j]::" +
		 * blocks[i][j]); } }
		 */
		System.out.println(board.toString());
		System.out.println("hamming::" + board.hamming());
		System.out.println("manhattan::" + board.manhattan());
		

		Iterable<Board> minPQ = board.neighbors();
		
		Iterator<Board> iterator = minPQ.iterator();
		while (iterator.hasNext()) {
			Board boardtemp = iterator.next();
			//System.out.println(boardtemp.toString()+";"+boardtemp.moves+";"+boardtemp.manhattan());
		}

Queue m = (Queue)minPQ;
		/*System.out.println(m.delMin().toString());
		System.out.println(m.delMin().toString());
		System.out.println(m.delMin().toString());*/
		
		
	}

	private class Queue implements Iterable<Board> {

		@Override
		public Iterator<Board> iterator() {
			// TODO Auto-generated method stub
			return new ListIterator();
		}
		
		public void insert(Board b){
			Node newNode = new Node(b,null);
			if(first == null){
				first = newNode;
				last = newNode;
			}else{
				Node old = last;
				old.next = newNode;
				last = newNode;
			}
			
		}
		
	}
	
	private class SortByPriority implements Comparator<Board>
    {
        public int compare(Board a, Board b)
        {
        	if (a.manhattan  < b.manhattan ) {
    			return -1;
    		} else {
    			return 1;
    		}
        	
        }
    }
	
	
	
	private static class Node<E> {
		E item;
		Node<E> next;

		Node( E element, Node<E> next) {
			this.item = element;
			this.next = next;
		}
	}

	private class ListIterator implements Iterator<Board> {

		private Node<Board> current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Board next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Board item = current.item;
			current = current.next;
			return item;
		}

	}

	/*public int getMoves() {
		return moves;
	}*/

}