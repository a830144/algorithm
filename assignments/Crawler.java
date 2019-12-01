package edu.clu.assignments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;


public class Crawler {

	public static void main(String[] args) {
		
		int[] arr = new int[]{41,18,15,12,8,6};
		char[] ch = new char[]{'a','b','c','d','e','f'}; 
		for(int i=0;i<arr.length;i++){
			int temp = arr[i];
			char ch0 = ch[i];
			for(int j=0;j<temp;j++){
				System.out.print(ch0);
			}
		}
		// TODO Auto-generated method stub
		/*Crawler crawler = new Crawler();
		crawler.streamPage();*/

	}

	public void streamPage() {
		URL url;
		URLConnection con = null;
		String line;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		Path currentRelativePath = Paths.get("");
		String filePath = currentRelativePath.toAbsolutePath().toString();
		String writeFile = filePath + System.getProperty("file.separator") +"output-file"+System.getProperty("file.separator")+"AAPL.html";

		try {
			url = new URL("https://finance.yahoo.com/quote/AAPL/");
			con = url.openConnection(); // throws an IOException
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			writer = new BufferedWriter(new FileWriter(writeFile));
			
			while((line = reader.readLine()) != null){
				writer.write(line);
				writer.newLine();
			}
			
			
			
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException ioe) {
				// nothing to see here
			}
		}
		
		
		 
	}
	
	
	/**
	* HTML parsing proceeds by calling a callback for
	* each and every piece of the HTML document. This
	* simple callback class simply prints an indented
	* structural listing of the HTML data.
	*/
	class HTMLParseLister extends HTMLEditorKit.ParserCallback
	    {
	    int indentSize = 0;
	    
	        protected void indent() {
	        indentSize += 3;
	    }
	        protected void unIndent() {
	        indentSize -= 3; if (indentSize < 0) indentSize = 0;
	    }
	    
	        protected void pIndent() {
	        for(int i = 0; i < indentSize; i++) System.out.print(" ");
	    }
	    
	        public void handleText(char[] data, int pos) {
	        pIndent();
	        System.out.println("Text(" + data.length + " chars)");
	    }
	    
	        public void handleComment(char[] data, int pos) {
	        pIndent();
	        System.out.println("Comment(" + data.length + " chars)");
	    }
	    
	        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	        pIndent();
	        System.out.println("Tag start(<" + t.toString() + ">, " +
	        a.getAttributeCount() + " attrs)");
	        indent();
	    }
	    
	        public void handleEndTag(HTML.Tag t, int pos) {
	        unIndent();
	        pIndent();
	        System.out.println("Tag end");};
	    
	        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	        pIndent();
	        System.out.println("Tag(<" + t.toString() + ">, " +
	        a.getAttributeCount() + " attrs)");
	    }
	    
	        public void handleError(String errorMsg, int pos){
	        System.out.println("Parsing error: " + errorMsg + " at " + pos);
	    }
	}

}
