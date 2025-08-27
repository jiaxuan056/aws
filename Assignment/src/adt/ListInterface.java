//Wai Kin
package adt;

import java.util.Comparator;

public interface ListInterface<T> extends Iterable<T> {
    void add(T item);
    boolean remove(T item);
    T get(int index);
    int size();
    boolean isEmpty();

    // Additional operations to support sorted behaviors used by modules
    boolean contains(T item);
    boolean addSorted(T item, Comparator<T> comparator);
    int lowerBound(T key, Comparator<T> comparator);
    void removeAt(int index);
    /**
     * Sorts the list using the provided comparator
     * @param comparator the comparator to use for sorting
     */
    void sort(Comparator<T> comparator);
}
