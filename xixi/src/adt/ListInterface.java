//Wai Kin
package adt;

import java.util.Comparator;

public interface ListInterface<T> extends Iterable<T>{
    boolean remove(T item);
    T get(int index);
    int size();
    boolean addSorted(T item, Comparator<T> comparator);
    int lowerBound(T key, Comparator<T> comparator);
    void removeAt(int index);
    void sort(Comparator<T> comparator);
    public boolean add(T newEntry);
    public T remove(int givenPostition);
    public boolean contains(T newEntry);
    public T getEntry(int givenPosition);
    public void clear();
    public int getNumberOfEntries();
    public boolean isEmpty();
    public boolean isFull();
}
