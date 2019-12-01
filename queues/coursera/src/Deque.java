import java.util.NoSuchElementException;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

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

	public Deque() {
		// construct an empty deque
	}

	public boolean isEmpty() {
		// is the deque empty?
		return size == 0;
	}

	public int size() {
		// return the number of items on the deque
		return size;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null) {
			throw new IllegalArgumentException();
		}
		final Node<Item> f = first;
		final Node<Item> newNode = new Node<>(null, item, f);
		first = newNode;
		if (f == null) {
			last = newNode;
		} else {
			f.prev = newNode;
		}
		size++;
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null) {
			throw new IllegalArgumentException();
		}
		final Node<Item> l = last;
		final Node<Item> newNode = new Node<>(l, item, null);
		last = newNode;
		if (l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		size++;
	}

	public Item removeFirst() {
		// remove and return the item from the front
		final Node<Item> f = first;
		if (f == null)
			throw new NoSuchElementException();
		final Item element = f.item;
		final Node<Item> next = f.next;
		f.item = null;
		f.next = null; // help GC
		first = next;
		if (next == null) {
			last = null;
		} else {
			next.prev = null;
		}
		size--;
		return element;
	}

	public Item removeLast() {
		// remove and return the item from the end
		final Node<Item> l = last;
		if (l == null)
			throw new NoSuchElementException();
		final Item element = l.item;
		final Node<Item> prev = l.prev;
		l.item = null;
		l.prev = null; // help GC
		last = prev;
		if (prev == null) {
			first = null;
		} else {
			prev.next = null;
		}
		size--;
		return element;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
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

		private Deque.Node<Item> current = first;

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