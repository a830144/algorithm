
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double STAT_VALUE = 1.96d;
	private final double[] statArray;
	private double mean;

	private double totalOpen;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid

		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		statArray = new double[trials];

		for (int trialCount = 0; trialCount < trials; trialCount++) {
			int result = percolate(n);
			// System.out.println("result is :" + result);
			double sampleThreshold = (double) result / (double) (n * n);
			// System.out.println("sampleThreshold is :" + sampleThreshold);
			statArray[trialCount] = sampleThreshold;

			totalOpen += result;
		}
	}

	private int percolate(int n) {
		Percolation percolation = new Percolation(n);
		ArrayMapping randomArr = new ArrayMapping(n * n);

		for (int i = 1; i <= n * n; i++) {
			randomArr.add(i);
		}
		int i = 1;
		while (i <= n * n) {

			int randomNo = randomArr.size() == 1 ? 0 : StdRandom.uniform(0, randomArr.size() - 1);
			int randomPosition = randomArr.get(randomNo);

			int row = randomPosition == n * n ? (randomPosition / n) : randomPosition / n + 1;
			int col = randomPosition % n + 1;

			randomArr.remove(randomNo);

			int[] value;

			value = new int[] { row, col };
			// System.out.println("rol :" + value[0] + ";col :" + value[1]);
			if (!percolation.isOpen(value[0], value[1])) {
				percolation.open(value[0], value[1]);
				i++;
			}

			// System.out.println("status :" + percolation.percolates() + "
			// ;number :" + i);
			if (percolation.percolates()) {
				// System.out.println("percolates :" );
				// System.out.println("number is :" + i);
				break;
			}

		}
		// System.out.println("number is :" + i + "
		// numberOfOpenSites()::"+percolation.numberOfOpenSites());
		return percolation.numberOfOpenSites();
	}

	public double mean() {
		// sample mean of percolation threshold
		mean = StdStats.mean(statArray);
		return mean;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(statArray);
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval

		return mean - (STAT_VALUE * stddev()) / Math.sqrt(statArray.length);

	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return mean + (STAT_VALUE * stddev()) / Math.sqrt(statArray.length);
	}

	public static void main(String[] args) {
		// test client (described below)
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(n, trials);
		System.out.println("mean in " + n + "*" + n + " " + trials + " times::" + percolationStats.mean());
		System.out.println("(2)mean in " + n + "*" + n + " " + trials + " times::"
				+ percolationStats.totalOpen / (double) (n * n * trials));
		System.out.println("stddev in " + n + "*" + n + " " + trials + " times::" + percolationStats.stddev());
		System.out.println("low endpoint of 95% confidence interval in " + n + "*" + n + " " + trials + " times::"
				+ percolationStats.confidenceLo());
		System.out.println("high endpoint of 95% confidence interval in " + n + "*" + n + " " + trials + " times::"
				+ percolationStats.confidenceHi());

	}

	private class ArrayMapping {

		/**
		 * The array buffer into which the elements of the ArrayList are stored.
		 * The capacity of the ArrayList is the length of this array buffer. Any
		 * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
		 * will be expanded to DEFAULT_CAPACITY when the first element is added.
		 */
		Object[] elementData; // non-private to simplify nested class access
		/**
		 * Shared empty array instance used for empty instances.
		 */
		private final Object[] emptyElementData = {};

		/**
		 * The size of the ArrayList (the number of elements it contains).
		 *
		 * @serial
		 */
		private int size = 0;

		/**
		 * Constructs an empty list with the specified initial capacity.
		 *
		 * @param initialCapacity
		 *            the initial capacity of the list
		 * @throws IllegalArgumentException
		 *             if the specified initial capacity is negative
		 */
		public ArrayMapping(int initialCapacity) {
			if (initialCapacity > 0) {
				this.elementData = new Object[initialCapacity];
			} else if (initialCapacity == 0) {
				this.elementData = emptyElementData;
			} else {
				throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
			}
		}

		/**
		 * This helper method split out from add(E) to keep method bytecode size
		 * under 35 (the -XX:MaxInlineSize default value), which helps when
		 * add(E) is called in a C1-compiled loop.
		 */
		private void add(int number) {
			elementData[size] = number;
			size++;
		}

		private void remove(int number) {
			elementData[number] = null;
			for (int i = number + 1; i < elementData.length; i++) {
				elementData[i - 1] = elementData[i];
			}
			size--;
		}

		public int size() {
			return size;
		}

		public int get(int index) {
			return (int) elementData[index];
		}

	}

}
