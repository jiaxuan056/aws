package adt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<T> implements ListInterface<T> {
    private final ArrayList<T> backingList;

    public SortedArrayList() {
        this.backingList = new ArrayList<>();
    }

    // ==== Extended helpers required by ListInterface ====
    @Override
    public boolean remove(T item) {
        return backingList.remove(item);
    }

    @Override
    public T get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public boolean addSorted(T item, Comparator<T> comparator) {
        int idx = lowerBound(item, comparator);
        backingList.add(idx, item);
        return true;
    }

    @Override
    public int lowerBound(T key, Comparator<T> comparator) {
        int low = 0;
        int high = backingList.size();
        while (low < high) {
            int mid = (low + high) >>> 1;
            T midVal = backingList.get(mid);
            if (comparator.compare(midVal, key) < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    @Override
    public void removeAt(int index) {
        backingList.remove(index);
    }

    @Override
    public void sort(Comparator<T> comparator) {
        backingList.sort(comparator);
    }

    // ==== Basic list operations (1-based variants kept for compatibility) ====
    @Override
    public boolean add(T newEntry) {
        return backingList.add(newEntry);
    }

    @Override
    public T remove(int givenPostition) {
        int idx = givenPostition - 1;
        if (idx < 0 || idx >= backingList.size()) return null;
        return backingList.remove(idx);
    }

    @Override
    public boolean contains(T newEntry) {
        return backingList.contains(newEntry);
    }

    @Override
    public T getEntry(int givenPosition) {
        int idx = givenPosition - 1;
        if (idx < 0 || idx >= backingList.size()) return null;
        return backingList.get(idx);
    }

    @Override
    public void clear() {
        backingList.clear();
    }

    @Override
    public int getNumberOfEntries() {
        return backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return backingList.iterator();
    }
}

