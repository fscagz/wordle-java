/*
 * interface for a set
 *
 * unordered collection, no duplicates allowed
 */

import java.util.Iterator;

public interface Set<T> extends Iterable<T>
{
    boolean add (T item);
    boolean remove (T item);
    T removeRandom();
    T getRandom();
    boolean contains (T item);
    int size();
    String toString();
    Iterator<T> iterator();
}
