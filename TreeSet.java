/*
 * TreeSet.java
 *
 * implements a set using a binary search tree
 *
 * Frank Scagluso Apr 26 2022
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeSet<T extends Comparable<T>> implements Set<T>
{
	private class Node
	{
		private T data;
		private Node left;
		private Node right;

		public Node (T item)
		{
			data = item;
			left = null;
			right = null;
		}
	}

	private Node root;
	private int size;
	private int counter = 0;

	public TreeSet()
	{
		root = null;
		size = 0;
	}

	public boolean add (T item)
	{
		checkForNull(item);
		if (size == 0) {
			root = new Node(item);
			size++;
			return true;
		}
		Node current = root;
		Node trail = null;
		while (current != null) {
			int comparison = item.compareTo(current.data);
			if (comparison == 0) return false;
			if (comparison > 0) {
				trail = current;
				current = current.right;
			}
			else {
				trail = current;
				current = current.left;
			}
		}
		if (item.compareTo(trail.data) > 0) {
			trail.right = new Node(item);
		}
		else {
			trail.left = new Node(item);
		}
		size++;
		return true;
	}

	public boolean contains (T item)
	{
		Node current = root;
		checkForNull(item);
		while (current != null)
		{
			int result = item.compareTo(current.data);

			if (result == 0)
			{
				return true;
			}

			if (result < 0)
			{
				current = current.left;
			}

			else
			{
				current = current.right;
			}
		}

		return false;
	}

	public boolean remove (T item)
	{
		Node current = root;
		Node parent = null;
		boolean onLeft = true;

		// traverse tree until find item
		while (current != null)
		{
			int result = item.compareTo(current.data);

			// smaller -> go left
			if (result < 0)
			{
				parent = current;
				current = current.left;
				onLeft = true;
			}

			// larger -> go right
			else if (result > 0)
			{
				parent = current;
				current = current.right;
				onLeft = false;
			}

			// item found
			else
			{
				// no children -> delete node
				if (current.left == null && current.right == null)
				{
					if (size == 1) root = null;
					else if (onLeft) parent.left = null;
					else parent.right = null;
				}

				// right child only -> replace parent with it
				else if (current.left == null)
				{
					if (current == root) root = current.right;
					else if (onLeft) parent.left = current.right;
					else parent.right = current.right;
				}

				// left child only -> replace parent with it
				else if (current.right == null)
				{
					if (current == root) root = current.left;
					else if (onLeft) parent.left = current.left;
					else parent.right = current.left;
				}

				// two children -> replace with largest predecessor
				// if node has child -> replace with child
				else
				{
					// save pointer to node to be replaced
					Node replace = current;

					// find largest predecessor
					parent = current;
					current = current.left;

					while (current.right != null)
					{
						parent = current;
						current = current.right;
					}

					// replace item to remove with largest predecessor
					replace.data = current.data;

					// if it has child, replace with child
					if (current.left != null)
						parent.right = current.left;
					else
						parent.right = null;
				}

				size--;
				return true;
			}
		}

		return false;
	}

	/* methods needed because part of interface but currently non-functional */ 

	public T getRandom()
	{
		return null;
	}

	public T removeRandom()
	{
		return null;
	}

	public int size()
	{
		return size;
	}

	/*
	 * returns data in sorted order
     *
     * uses inorder traversal
	 */

	public String toString()
	{
		if (root == null)
		{
			return "[]";
		}

		String s = "[" + inorder(root, new StringBuilder());
		return s.substring(0, s.length()-2) + "]";
	}

	private String inorder(Node n, StringBuilder s)
	{
		if (n.left != null)
			inorder (n.left, s);

		s.append(n.data + ", ");

		if (n.right != null)
			inorder(n.right, s);

		return s.toString();
	}

	public Iterator<T> iterator()
	{
		return new TreeIterator();
	}

	private void checkForNull (T item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Item can't be null");
		}
	}

	private class TreeIterator implements Iterator<T>
	{
		private Stack<Node> nodes;    	
		private Node currentNode;  	

		public TreeIterator()
		{
			nodes = new LinkedStack<>();
			currentNode = root;
		}

		public boolean hasNext()
		{
			return nodes.size() != 0 || currentNode != null;
		}

		public T next()
		{
        	Node nextNode = null;

        	while (currentNode != null)
        	{
                nodes.add(currentNode);          	
                currentNode = currentNode.left;		 
            }
            
			if (nodes.size() != 0)				
            {
                nextNode = nodes.remove();
                currentNode = nextNode.right;
            }

			else
			{
				throw new NoSuchElementException ("Off end of list");
			}

			return nextNode.data;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
