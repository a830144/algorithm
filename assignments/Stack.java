/******************************************************************************
 *  Compilation:  javac Stack.java
 *  Execution:    
 *       1. put the input file in the folder of /project-folder/input-file
 *       2. java Stack input.txt
 *  Data files:   https://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  A generic stack, implemented using a singly linked list.
 *  Each stack element is of type Item.
 *
 *  This version uses a static nested class Node (to save 8 bytes per
 *  Node), whereas the version in the textbook uses a non-static nested
 *  class (for simplicity).
 *  
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Stack  tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

package edu.clu.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * 
 * @author Yu-Chun,Chou
 * @since 06/04/2018
 * @version 1.0 
 *
 */
public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;     // top of stack
    private int n;                // size of the stack

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  item the item to add
     */
    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        //3. Print the list as you pushed them
        System.out.print(" " +item.toString());
        n++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        //5. Print out the reversed list
        System.out.print(" " +item.toString());
        return item;                   // return the saved item
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     * @throws NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
       

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     *
     * @return an iterator to this stack that iterates through the items in LIFO order
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


    /**
     * Unit tests the {@code Stack} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	String fileName = args[0];
    	Stack<String> stack = new Stack<String>();
    	Path currentRelativePath = Paths.get("");
		String filePath = currentRelativePath.toAbsolutePath().toString();
		String readFile = filePath + System.getProperty("file.separator") +"input-file"+System.getProperty("file.separator")+fileName;
    	
		//1.Read in the strings contained in tobe.txt (in your download)
    	Scanner sc = null;
        try {
            sc = new Scanner(new File(readFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
        System.out.print("Original Strings:");
        while (sc.hasNext()) {
           String item = sc.next();
           //2.Push them onto a stack
           //3.Print the list as you pushed them will be inside function push(Item)
           stack.push(item);           

        }
        System.out.println("");
        
        System.out.print("Reversed Strings:");
    	
    	while(!stack.isEmpty()){
    		//4. Now pop them off the stack in reverse order (to the order you pushed)
    		//5. Print out the reversed list will be inside function pop()
    		stack.pop();
    	}
    }
}
