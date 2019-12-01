
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size = 0;

	/**
	 * Pointer to first node. Invariant: (first == null && last == null) ||
	 * (first.prev == null && first.item != null)
	 */
	private Node<Item> first;

	/**
	 * Pointer to last node. Invariant: (first == null && last == null) ||
	 * (last.next == null && last.item != null)
	 */
	private Node<Item> last;

	public RandomizedQueue() {
		// construct an empty randomized queue
	}

	public boolean isEmpty() {
		// is the randomized queue empty?
		return size == 0;
	}

	public int size() {
		// return the number of items on the randomized queue
		return size;
	}

	public void enqueue(Item item) {
		// add the item
		Node<Item> l = last;
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node<Item> next = new Node<>(last, item, null);
		last = next;
		if(isEmpty()){
			first = next;
		}else{
			l.next = next;
		}
		size++;
	}

	public Item dequeue() {
		// remove and return a random item
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		return sample();
	}

	public Item sample() {
		// return a random item (but do not remove it)
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int random = StdRandom.uniform(size);
		random = (random==0?1:random);
		Node<Item> currentNode = first;
		Node<Item> tempNode = null;
		int temp = 1;
		while (currentNode != null && temp<=size) {
			if (temp == random) {
				tempNode = currentNode;
				if (currentNode.prev!=null && currentNode.next != null) {
					currentNode.prev.next = currentNode.next;
				} else if (currentNode.prev==null && currentNode.next != null){
					first = currentNode.next;
				} else if (currentNode.prev!=null && currentNode.next == null){
					currentNode.prev.next = null;
				} else {
					first = null;
					last = null;
				}
				break;
			}
			currentNode = currentNode.next;
			temp++;
		}
		size--;
		return tempNode.item;
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new ListIterator();
	}

	public static void main(String[] args) {
		// unit testing (optional)
	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	private class ListIterator implements Iterator<Item> {

		private Node<Item> current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

	}
}