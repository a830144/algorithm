package edu.clu.assignments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StdIn {
	BufferedReader reader;
	
	public BufferedReader getReader(String fileName){
		Path currentRelativePath = Paths.get("");
		String filePath = currentRelativePath.toAbsolutePath().toString();
		String readFile = filePath + System.getProperty("file.separator") +"input-file"+System.getProperty("file.separator")+fileName;
	    try {
			reader = new BufferedReader(new FileReader(readFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    return reader;
	}

}
