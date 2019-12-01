import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final WeightedQuickUnionUF wquf;
	private final WeightedQuickUnionUF wquf2;
	private boolean[][] sites;
	private final int gridLength;
	private int openSitesCount;
	private final int firstRow;
	private final int lastRow;
	private final int firstCol;
	private final int lastCol;

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		wquf = new WeightedQuickUnionUF(n * n);
		wquf2 = new WeightedQuickUnionUF((n * n) + 2);
		sites = new boolean[n][n];
		gridLength = n;
		firstRow = 0;
		lastRow = n - 1;
		firstCol = 0;
		lastCol = n - 1;

	}

	public void open(int row, int col) {
		if (row <= 0 || col <= 0 || row > gridLength || col > gridLength) {
			throw new IllegalArgumentException();
		}
		if (row > gridLength || col > gridLength) {
			throw new ArrayIndexOutOfBoundsException();
		}

		// open site (row, col) if it is not open already
		if (!isOpen(row, col)) {
			row = row - 1;
			col = col - 1;
			sites[row][col] = true;

			if (row == firstRow) {
				int pwqufPosition = col;
				int qwqufPosition = (gridLength) * (gridLength);
				wquf2.union(pwqufPosition, qwqufPosition);
			}
			if (row == lastRow) {
				int pwqufPosition = row * gridLength + col;
				int qwqufPosition = (gridLength) * (gridLength) + 1;
				wquf2.union(pwqufPosition, qwqufPosition);
			}
			if ((row != firstRow) && sites[row - 1][col]) {
				int pwqufPosition = (row - 1) * gridLength + col;
				int qwqufPosition = row * gridLength + col;
				wquf.union(pwqufPosition, qwqufPosition);
				wquf2.union(pwqufPosition, qwqufPosition);
			}

			if ((row != lastRow) && sites[row + 1][col]) {
				int pwqufPosition = (row + 1) * gridLength + col;
				int qwqufPosition = row * gridLength + col;
				wquf.union(pwqufPosition, qwqufPosition);
				wquf2.union(pwqufPosition, qwqufPosition);
			}

			if ((col != firstCol) && sites[row][col - 1]) {
				int pwqufPosition = (row) * gridLength + (col - 1);
				int qwqufPosition = row * gridLength + col;
				wquf.union(pwqufPosition, qwqufPosition);
				wquf2.union(pwqufPosition, qwqufPosition);
			}

			if ((col != lastCol) && sites[row][col + 1]) {
				int pwqufPosition = (row) * gridLength + (col + 1);
				int qwqufPosition = row * gridLength + col;
				wquf.union(pwqufPosition, qwqufPosition);
				wquf2.union(pwqufPosition, qwqufPosition);
			}

			openSitesCount++;
		}

	}

	public boolean isOpen(int row, int col) {
		if (row <= 0 || col <= 0 || row > gridLength || col > gridLength) {
			throw new IllegalArgumentException();
		}
		if (row > gridLength || col > gridLength) {
			throw new ArrayIndexOutOfBoundsException();
		}
		row = row - 1;
		col = col - 1;
		// is site (row, col) open?
		return sites[row][col];
	}

	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		if (row <= 0 || col <= 0 || row > gridLength || col > gridLength) {
			throw new IllegalArgumentException();
		}
		boolean returnVal = false;
		if (isOpen(row, col)) {
			if (row > 1) {
				row = row - 1;
				col = col - 1;
				int pwqufPosition = (gridLength) * (gridLength);
				int qwqufPosition = (row) * gridLength + col;
				if (wquf2.connected(pwqufPosition, qwqufPosition)) {
					returnVal = true;
				}
				/*
				 * row = row - 1; col = col - 1; int qwqufPosition = (row) *
				 * gridLength + col; int root = wquf.find(qwqufPosition); for
				 * (int i = 0; i < gridLength && returnVal ==false; i++) { int
				 * pwqufPosition = i; if(wquf.find(pwqufPosition)==root){
				 * returnVal = true; break; } }
				 */
				/*
				 * if (0 <= root && root < gridLength) { returnVal = true; }
				 */
				/*
				 * for (int i = 0; i < gridLength && returnVal ==false; i++) {
				 * int pwqufPosition = i; if (wquf.connected(pwqufPosition,
				 * qwqufPosition)) { returnVal = true; break; // return true; }
				 * 
				 * }
				 */
			} else {
				returnVal = true;
				// return true;
			}
		}
		// return false;
		return returnVal;
	}

	public int numberOfOpenSites() { // number of open sites
		return openSitesCount;
	}

	public boolean percolates() {
		// does the system percolate?

		boolean returnVal = false;
		if (gridLength == 1) {
			if (isOpen(1, 1)) {
				returnVal = true;
				// return true;
			} else {
				returnVal = false;
				// return false;
			}
		} else {
			if (wquf2.connected((gridLength) * (gridLength), (gridLength) * (gridLength) + 1)) {
				returnVal = true;
			}

			/*
			 * for (int i = 0; i < gridLength ; i++) { int qwqufPosition =
			 * (gridLength - 1) * gridLength + i; int root =
			 * wquf.find(qwqufPosition); for (int j = 0; j < gridLength; j++) {
			 * int pwqufPosition = j; if(wquf.find(pwqufPosition)==root){
			 * returnVal = true; break; } }
			 * 
			 * }
			 */

		}
		return returnVal;
		// return false;
	}

	public static void main(String[] args) {
		Percolation percolation = new Percolation(1);
		System.out.println("percolates:" + percolation.percolates());
		System.out.println("isFull:" + percolation.isFull(1, 1));
		System.out.println("isOpen:" + percolation.isOpen(1, 1));

		/*
		 * percolation.open(1, 6);
		 * 
		 * System.out.println("percolates:" + percolation.percolates());
		 * System.out.println("isFull:" + percolation.isFull(1, 6));
		 * System.out.println("isOpen:" + percolation.isOpen(1, 6));
		 */
		/*
		 * Percolation percolation = new Percolation(5); int[] value;
		 * 
		 * 
		 * value = percolation.arrayMapping(2,4); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * value = percolation.arrayMapping(3,3); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * 
		 * value = percolation.arrayMapping(4,4); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * value = percolation.arrayMapping(5,4); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * value = percolation.arrayMapping(1,4); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * value = percolation.arrayMapping(3,4); percolation.open(value[0],
		 * value[1]); System.out.println("status :"+percolation.percolates());
		 * 
		 * /* percolation.open(1, 4);
		 * System.out.println("status :"+percolation.percolates());
		 * percolation.open(3, 4);
		 * System.out.println("status :"+percolation.percolates());
		 */
	}

}
