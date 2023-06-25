/*
 * HashSet.java
 *
 * implements a set using a hash table with chains of nodes
 *
 * Frank Scagluso Apr 19 2022
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<T> implements Set<T>
{
	private class Node
	{
		private T data;
		private Node next;

		public Node(T item)
		{
			data = item;
			next = null;
		}

		public String toString()
		{
			return "" + data;
		}
	}

	public static final int DEFAULT_CAPACITY = 19;
	public static final double LOAD_FACTOR = 1.0;

	private Node[] collection;
	private int size;

	public HashSet ()
	{
		this(DEFAULT_CAPACITY);
	}

	public HashSet (int capacity)
	{
		@SuppressWarnings("unchecked")
		Node [] temp = (Node[]) new HashSet.Node[capacity];
		collection = temp;
		temp = null;
		size = 0;
	}

	public boolean add (T item)
	{
		checkForNull(item);
		int index = getHashCode(item);
		Node current = collection[index];
		while (current != null)
		{
			if (current.data.equals(item))
			{
				return false;
			}
			current = current.next;
		}
		ensureSpace();
		Node newNode = new Node(item);
		newNode.next = collection[index];
		collection[index] = newNode;
		size++;
		return true;
	}	

	public boolean remove (T item)
	{
		if (size == 0) return false;
		checkForNull(item);
		int index = getHashCode(item);
		Node current = collection[index];
		while (current != null) {
			if (current.data.equals(item)) {
				current.data = collection[index].data;
				collection[index] = collection[index].next;
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public boolean contains(T item)
	{
		checkForNull(item);
		int index = getHashCode(item);
		Node current = collection[index];

		while (current != null)
		{
			if (item.equals(current.data))
			{
				return true;
			}

			current = current.next;
		}

		return false;
	}

	public T removeRandom()
	{
		if (size == 0) return null;
		boolean filled = false;
		int random = 0;

		while (!filled)
		{
			random = (int)(Math.random() * size);

			if (collection[random] != null)
			{
				filled = true;
			}
		}

		T removed = collection[random].data;
		collection[random] = collection[random].next;
		size--;
		return removed;
	}

	public T getRandom()
	{
		if (size == 0) 
		{
			return null;
		}

		boolean filled = false;
		int random = 0;

		while (!filled)
		{
			random = (int)(Math.random() * size);

			if (collection[random] != null)
			{
				filled = true;
			}
		}

		return collection[random].data;
	}

	public int size()
	{
		return size;
	}

	public String toString()
	{
		String s = "";

		for (int i = 0; i < collection.length; i++)
		{
			Node current = collection[i];
			String n = String.format("%2d", i);
			if (current == null) s+= n + "\t" + " " + "\n";

			else
			{
				s+= n + "\t" + current + " ";

				while (current.next != null)
				{
					current = current.next;
					s+= current + " ";
				}

				s+= "\n";
			}
		}

		return s;
	}

	public Iterator<T> iterator()
	{
		return new HashIterator();
	}

	private void checkForNull(T item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("null not a possible value!");
		}
	}

	private void ensureSpace()
	{
		if (size/collection.length * 1. >= LOAD_FACTOR)
		{
			Node [] original = collection;
			@SuppressWarnings("unchecked")
			Node [] larger = (Node[]) new HashSet.Node[nextPrime(size*2)];
			collection = larger;

			for (int i = 0; i < original.length; i++)
			{
				Node current = original[i];
				while (current!= null)
				{
					T item = current.data;
					int index = getHashCode(item);
					Node newNode = new Node (item);
					newNode.next = collection[index];
					collection [index] = newNode;
					current = current.next;
				}
			}

			original = null;
			larger = null;
		}
	}

	private static int nextPrime (int n)
	{
		boolean prime = false;

		while (!prime)
		{
			n++;
			prime = true;
			for (int i = 2; i < n; i++)
			{
				if (n % i == 0)
				{
					prime = false;
					break;
				}
			}
		}

		return n;
	}

	private int getHashCode(T item)
	{
		int index = item.hashCode() % collection.length;

		if (index < 0)
		{
			index += collection.length;
		}

		return index;
	}

	private class HashIterator implements Iterator<T>
	{
		private Node position;
		private int bucket;
		private int count;

		public HashIterator()
		{
			position = collection[0];
			bucket = 0;
			count = 0;
		}

		public boolean hasNext()
		{
			return count < size;
		}

		public T next()
		{
			if (hasNext())
			{
				while (position == null)
				{
					position = collection[++bucket];
				}

				T removed = position.data;
				position = position.next;
				count++;
				return removed;
			}

			else
			{
				throw new NoSuchElementException("off end of list");
			}
		}

		public void remove()
		{
			throw new UnsupportedOperationException("remove not implemented");
		}
	}
}
