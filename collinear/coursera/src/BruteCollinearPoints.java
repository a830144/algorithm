

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] lineSegments;
	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		if(points == null){
			throw new java.lang.IllegalArgumentException();
		}
		for(int i=0;i<points.length;i++){
			if(points[i]==null){
				throw new java.lang.IllegalArgumentException();
			}
			for(int j=i+1;j<points.length;j++){
				if (points[i].compareTo(points[j])==0){
					throw new java.lang.IllegalArgumentException();
				}
			}
		}
		this.points = points;
		
	}

	public int numberOfSegments() {
		// the number of line segments
		return lineSegments.length;
	}

	public LineSegment[] segments() {
		// the line segments
		Node first = new Node();
		Node last = first;
		for(int p=0;p<points.length-3;p++){
			for(int q=p+1;q<points.length-2;q++){
				for(int r=q+1;r<points.length-1;r++){
					for(int s=r+1;s<points.length;s++){
						if((points[p].slopeTo(points[q]))==(points[p].slopeTo(points[r]))&&(points[p].slopeTo(points[r])) ==(points[p].slopeTo(points[s]))){
							
							Point min = points[p];
							Point max = points[p];
							if(points[q].compareTo(min) < 0){
								min = points[q];
							}
							if(points[q].compareTo(max) > 0){
								max = points[q];
							}
							if(points[r].compareTo(min) < 0){
								min = points[r];
							}
							if(points[r].compareTo(max) > 0){
								max = points[r];
							}
							if(points[s].compareTo(min) < 0){
								min = points[s];
							}
							if(points[s].compareTo(max) > 0){
								max = points[s];
							}
							Node newNode = new Node();
							newNode.lineSegment = new LineSegment(min,max);
							Node old =last;
							old.next = newNode;
							last = newNode;
							
						}
					}
				}
			}
		}
		int count =0;
		Node next= first.next;
		while(next!=null){
			count++;
			next =next.next;			
		}
		lineSegments = new LineSegment[count];
		Node nextIter= first.next;
		count =0;
		while(nextIter!=null){
			lineSegments[count]= nextIter.lineSegment;
			nextIter =nextIter.next;	
			count++;
		}

		return lineSegments;
	}
	
	private class Node{
		LineSegment lineSegment;
		Node next;
	}
}
