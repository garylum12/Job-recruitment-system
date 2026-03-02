package adt;

/**
 * @author CHANG JIA JIN
 */
public interface SortedListInterface<T extends Comparable<T>> {

    public boolean add(T newEntry);

    public boolean remove(T anEntry);

    public boolean contains(T anEntry);

    public void clear();

    public int getNumberOfEntries();

    public boolean isEmpty();

    //this method to help searching
    public Object[] getAllEntries();

    public T getEntry(int index);

}
