/* 
 * used for tree iterators 
 */

import java.util.Iterator;

public interface Stack<T>
{
	void  add (T item);
	boolean remove (T item);
	T remove();
	T get();
	boolean contains (T item);
	int size();
	String toString();
	Iterator<T> iterator();
}
