/*
 * used for tree iterators
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<T> implements Stack<T>
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
	}

	private Node head;
	private int size;

	public LinkedStack()
	{
		head = null;
		size = 0;
	}

	public void add(T item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("null not a possible value!");
		}

		Node newest = new Node (item);
		newest.next = head;
		head = newest;
		size++;
	}

	public boolean remove(T item)
	{
		Node current = head;

		for (int i = 0; i < size; i++)
		{
			if (item.equals(current.data))
			{
				current.data = head.data;
				head = head.next;
				size--;
				return true;
			}

			current = current.next;
		}

		return false;
	}

	public T remove()
	{
		if (size == 0) return null;
		T firstItem = head.data;
		head = head.next;
		size--;
		return firstItem;
	}

	public T get()
	{
		if (size == 0)
		{
			return null;
		}

		return head.data;
	}

	public boolean contains(T item)
	{
		Node current = head;

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

	public int size()
	{
		return size;
	}

	public String toString()
	{
		String s = "[";
		Node current = head;

		while (current != null)
		{
			s += current.data;

			if (current.next != null)
			{
				s += ", ";
			}

			current = current.next;
		}

		return s + "]";
	}

	public Iterator<T> iterator()
	{
		return new LinkedIterator();
	}

	private class LinkedIterator implements Iterator<T>
	{
		private Node position;  	

		public LinkedIterator()
		{
			position = head;
		}

		public boolean hasNext()
		{
			return position!= null;
		}

		public T next()
		{
			if (hasNext())
			{
				T item = position.data;
				position = position.next;
				return item;
			}

			else
			{
				throw new NoSuchElementException ("Off end of list");
			}
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
