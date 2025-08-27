package adt;

import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<T extends Comparable<T>> implements ListInterface<T> {

    private T[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Constructs an empty list with an initial capacity of 16.
     */
    @SuppressWarnings("unchecked")
    public SortedArrayList() {
        this.data = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds a new entry to the end of this list.
     * The list's capacity is doubled if it is full.
     *
     * @param newEntry The object to be added as a new entry.
     */
    @Override
    public void add(T newEntry) {
        ensureCapacity(size + 1);
        data[size++] = newEntry;
    }

    /**
     * Adds a new entry at a specified position within this list.
     * Entries to the right of the new entry are shifted to the right.
     *
     * @param newPosition An integer that specifies the desired position of the new entry.
     * @param newEntry    The object to be added as a new entry.
     * @return true if the addition is successful, false if not.
     */
    public boolean add(int newPosition, T newEntry) {
        if (newPosition < 0 || newPosition > size) {
            return false; // Or throw IndexOutOfBoundsException
        }
        ensureCapacity(size + 1);
        // Shift elements to make space for the new entry
        System.arraycopy(data, newPosition, data, newPosition + 1, size - newPosition);
        data[newPosition] = newEntry;
        size++;
        return true;
    }

    /**
     * Removes the entry at a given position from this list.
     *
     * @param givenPosition An integer that indicates the position of the entry to be removed.
     * @return A reference to the removed entry.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T remove(int givenPosition) {
        checkIndex(givenPosition);
        T removedEntry = data[givenPosition];
        // Shift elements to the left to fill the gap
        int numMoved = size - givenPosition - 1;
        if (numMoved > 0) {
            System.arraycopy(data, givenPosition + 1, data, givenPosition, numMoved);
        }
        data[--size] = null; // Clear the last element to allow garbage collection
        return removedEntry;
    }

    /**
     * Removes the first occurrence of a specific entry from this list.
     *
     * @param anEntry The entry to be removed.
     * @return true if the removal is successful, false if not.
     */
    @Override
    public boolean remove(T anEntry) {
        int index = indexOf(anEntry);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Removes all entries from this list.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Replaces the entry at a given position in this list.
     *
     * @param givenPosition An integer that indicates the position of the entry to be replaced.
     * @param newEntry      The object that will replace the entry.
     * @return true if the replacement is successful.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public boolean replace(int givenPosition, T newEntry) {
        checkIndex(givenPosition);
        data[givenPosition] = newEntry;
        return true;
    }

    /**
     * Retrieves the entry at a given position in this list.
     *
     * @param givenPosition An integer that indicates the position of the desired entry.
     * @return A reference to the indicated entry.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    @Override
    public T get(int givenPosition) {
        checkIndex(givenPosition);
        return data[givenPosition];
    }

    /**
     * Sees whether this list contains a given entry.
     *
     * @param anEntry The object that is the desired entry.
     * @return true if the list contains anEntry, or false if not.
     */
    @Override
    public boolean contains(T anEntry) {
        return indexOf(anEntry) >= 0;
    }

    /**
     * Gets the number of entries in this list.
     *
     * @return The integer number of entries currently in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Sees whether this list is empty.
     *
     * @return true if the list is empty, or false if not.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Sorts the list in place using Insertion Sort based on the provided comparator.
     * This method allows for on-demand sorting with custom logic.
     *
     * @param comparator The logic used for comparing elements.
     */
    @Override
    public void sort(Comparator<T> comparator) {
        if (size <= 1) return;
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[size];
        mergeSort(0, size - 1, comparator, aux);
    }

    private void mergeSort(int left, int right, Comparator<T> comparator, T[] aux) {
        if (left >= right) return;
        int mid = (left + right) >>> 1;
        mergeSort(left, mid, comparator, aux);
        mergeSort(mid + 1, right, comparator, aux);
        // already ordered check
        if (comparator.compare(data[mid], data[mid + 1]) <= 0) return;
        merge(left, mid, right, comparator, aux);
    }

    private void merge(int left, int mid, int right, Comparator<T> comparator, T[] aux) {
        int i = left, j = mid + 1, k = left;
        for (int x = left; x <= right; x++) aux[x] = data[x];
        while (i <= mid && j <= right) {
            if (comparator.compare(aux[i], aux[j]) <= 0) {
                data[k++] = aux[i++];
            } else {
                data[k++] = aux[j++];
            }
        }
        while (i <= mid) data[k++] = aux[i++];
        while (j <= right) data[k++] = aux[j++];
    }

  

    /**
     * Inserts the given item according to the provided comparator to keep ordering.
     * @param item The item to insert.
     * @param comparator The comparator defining the sort order.
     * @return true if inserted successfully.
     */
    @Override
    public boolean addSorted(T item, Comparator<T> comparator) {
        int pos = lowerBound(item, comparator);
        return add(pos, item);
    }

    /**
     * Returns the first index i in [0, size] such that comparator.compare(data[i], key) >= 0.
     * If all elements are smaller, returns size. This is useful for finding where to insert an item.
     * @param key The item to find the insertion point for.
     * @param comparator The comparator used for ordering.
     * @return The insertion point index.
     */
    @Override
    public int lowerBound(T key, Comparator<T> comparator) {
        int lo = 0, hi = size;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1; // Safe midpoint calculation
            if (comparator.compare(data[mid], key) < 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    /**
     * Removes the item at a specific index. This is an alias for the other remove method.
     * @param index The index of the item to remove.
     */
    @Override
    public void removeAt(int index) {
        remove(index);
    }

    // ============== PRIVATE HELPER METHODS ==============

    /**
     * Increases the capacity of the array, if necessary, to ensure that
     * it can hold at least the number of elements specified by the minimum
     * capacity argument.
     *
     * @param minCapacity the desired minimum capacity.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length * 2);
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Comparable[newCapacity];
            System.arraycopy(data, 0, newArray, 0, size);
            data = newArray;
        }
    }

    private int indexOf(T anEntry) {
        for (int i = 0; i < size; i++) {
            if ((anEntry == null && data[i] == null) || (anEntry != null && anEntry.equals(data[i]))) {
                return i;
            }
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                return data[cursor++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
