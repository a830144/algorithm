package edu.clu.assignments;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 * @author Yu-Chun,Chou
 * @since 05/28/2018
 * @version 1.0 
 *
 */
/*
 * This program will use Assn2and3.txt as input file,count the actual number 
 * of array accesses with a variable ¡V disregard the array initialization 
 */
public class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of sites in subtree rooted at i
    private int count;      // number of components
    private static int totalAccesses =0; //number of array access

    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        //don't count the array accesses 
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int findCount = 0;
        while (p != parent[p]){
            p = parent[p];
            findCount++;
            totalAccesses++;
        }
        //even it's a root node, it also will access the array once
        if(findCount == 0){
        	findCount = 1;
        	totalAccesses++;
        }
        System.out.println("** find :: the access the array to find root node " + p + " is "+findCount );
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
    	System.out.println(" <determine 2 node ary connected :: find begin>");
    	boolean result = (find(p) == find(q));
    	System.out.println(" <determine 2 node ary connected :: find end>");
    	return result;
    }

    /**
     * Merges the component containing site {@code p} with the 
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
    	System.out.println(" <union subroutine :: find begin> ");
        int rootP = find(p);
        int rootQ = find(q);
        System.out.println(" <union subroutine :: find end> ");
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        totalAccesses = totalAccesses + 5;
        System.out.println("** merge :: the access the array is 5 to merge root node " + p + " and root node "+q );
        count--;
    }


    /**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
     * where each integer represents some object;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	StdIn stdin = new StdIn();
    	BufferedReader reader = stdin.getReader("Assn2and3.txt");
    	String line;
    	try {
			line = reader.readLine();
			int n = Integer.parseInt(line);
			int lastTotalAccess=0;
			WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
			while ((line = reader.readLine()) != null) {
				String[] strArr = line.split(" ");
				int p = Integer.parseInt(strArr[0]);
				int q = Integer.parseInt(strArr[1]);
				if (uf.connected(p, q)) continue;
			    uf.union(p, q);
			    
			    System.out.println(p + " - " + q + " requires " + (totalAccesses-lastTotalAccess) + " accesses" );
			    lastTotalAccess = totalAccesses;
			    System.out.println("---------------------- ");
			}
			System.out.println("There is total " +totalAccesses + " array access (not count array initialization) and " + uf.count() + " components");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}


/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/