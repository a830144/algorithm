import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private final Point[] points;
	private LineSegment[] lineSegments;

	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new java.lang.IllegalArgumentException();
			}
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new java.lang.IllegalArgumentException();
				}
			}
		}
		this.points = points;
	}

	public int numberOfSegments() {
		// the number of line segments
		return 0;
	}

	public LineSegment[] segments() {
		// the line segments
		Node first = new Node();
		Node last = first;
		Point[] temp = new Point[points.length];
		for (int outer = 0; outer < points.length; outer++) {
			// begin
			Point p = points[outer];

			/*System.out.println("p x::" + p.toString());*/
			Comparator<Point> sortBySlope = p.slopeOrder();

			int newArrPosition = 0;
			for (int i = 0; i < points.length; i++) {
				if (i != outer) {
					temp[newArrPosition++] = points[i];
				}
			}
			Arrays.sort(temp, 0, temp.length - 1, sortBySlope);
			temp[temp.length - 1] = p;
			int count = 1;
			int firstPos = 0;
			int lastPos = 0;
			for (int n = 0; n < temp.length - 1; n++) {
				/*System.out.println("temp[n] ::" + temp[n].toString() + " to temp[p]::" + p.toString()
						+ ";temp[n].slopeTo(p)::" + temp[n].slopeTo(p));*/
				if (temp[n].slopeTo(p) == temp[n + 1].slopeTo(p)) {
					if(count==1){
						firstPos = n;
					}
					count++;
				} else {
					if (count >= 3) {
						lastPos = n;
						Point min = temp[firstPos];
						Point max = temp[firstPos];
						for (int i = firstPos; i <= lastPos; i++) {
							if (temp[i].compareTo(min) < 0) {
								min = temp[i];
							}
							if (temp[i].compareTo(max) > 0) {
								max = temp[i];
							}
						}
						if (p.compareTo(min) < 0) {
							min = p;
						}
						if (p.compareTo(max) > 0) {
							max = p;
						}

						Node newNode = new Node();
						/*System.out.println("min max::" + min + ";" + max);*/
						newNode.lineSegment = new LineSegment(min, max);

						boolean hasValue = false;
						Node next = first.next;
						while (next != null) {
							if (next.lineSegment.toString().equals(newNode.lineSegment.toString())) {
								hasValue = true;
							}
							next = next.next;
						}
						if (!hasValue) {
							Node old = last;
							old.next = newNode;
							last = newNode;
						}
					}
					count = 1;
					
				}

			}
		}
		// end
		int count = 0;
		Node next = first.next;
		while (next != null) {
			count++;
			next = next.next;
		}
		lineSegments = new LineSegment[count];
		Node nextIter = first.next;
		count = 0;
		while (nextIter != null) {
			lineSegments[count] = nextIter.lineSegment;
			nextIter = nextIter.next;
			count++;
		}

		return lineSegments;
	}

	private class Node {
		LineSegment lineSegment;
		Node next;
	}
}
