package edu.clu.assignments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * 
 * @author Yu-Chun,Chou
 * @since 05/28/2018
 * @version 1.0 
 *
 */
/*
 * This program will use 4Kints.txt as input file, and count the number of pairs that sum to exactly 0;
 * If pairs means the sequence two numbers in the input file, the 
 */
public class Zeropairs {

	public static void main(String[] args) {
		Stopwatch stopwatch =new Stopwatch();
		Zeropairs zeropairs = new Zeropairs();
		Path currentRelativePath = Paths.get("");
		String filePath = currentRelativePath.toAbsolutePath().toString();

		String readFile = filePath + System.getProperty("file.separator") +"input-file"+System.getProperty("file.separator")+"64Kints.txt";
		String writeFile = filePath + System.getProperty("file.separator") +"output-file"+System.getProperty("file.separator")+"output-assignment1-1.txt";
		//int countSumZero = zeropairs.processPairZero(readFile,writeFile);
		int countSumZero = zeropairs.processPairZeroInWholeFile(readFile,writeFile);
		System.out.println("Zeropairs ::the number of pairs that sum to exactly 0 is " + countSumZero);
		double time = stopwatch.elapsedTime();
		System.out.println("The time cost in Zeropairs is ::"+time);
	}
	/**
	 * the function read the input file ,store them in the array, and see if any two numbers will sum as
	 * zero, then tey will be a pair
	 */
	private int processPairZeroInWholeFile(String filenameReader, String filenameWriter) {
		int lineValue = 0;
		int countSumZero = 0;

		BufferedReader reader;
		BufferedWriter writer;
		try {
			reader = new BufferedReader(new FileReader(filenameReader));
			writer = new BufferedWriter(new FileWriter(filenameWriter));
			String line;
			int size = 64000;
			int[] intArr = new int[size];
			int pointer = 0;	
			//PUT DATA INTO INITIAL ARRY ; big(o) = n
			while ((line = reader.readLine()) != null) {
				lineValue = Integer.parseInt(line.trim());
				intArr[pointer] = lineValue;			
				pointer++;
			}
			
			//MERGE SORT ;big(o) = nlog(n)
			Arrays.sort(intArr);
	        
			//BINARY SEARCH ;big(o) = log(n)
			for(int i =0; i< intArr.length;i++){
				int first = intArr[i];
				int match = first *(-1);
				int result = Arrays.binarySearch(intArr,match);
				if(result >=0){
				    System.out.println("first index :: "+ i +"; value :: " +first + " found match at index = " + result +" ;value :: "+match);
				    writer.write("first index :: "+ i +"; value :: " +first + " found match at index = " + result +" ;value :: "+match);
					writer.newLine();
					countSumZero++;
				}
			}						
			writer.flush();			
			reader.close();
			writer.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return countSumZero;

	}

	
	
}
