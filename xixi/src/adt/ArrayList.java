package adt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class ArrayList<T> implements ListInterface<T> {
    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    
    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }
    
    public ArrayList(int initialCapacity){
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }
    
    public boolean add(T newEntry) {
    if (isArrayFull()) {
      doubleArray();
    }

    array[numberOfEntries] = newEntry;
    numberOfEntries++;
    return true;
  }
    
    public boolean add(int newPosition, T newEntry) {
    boolean isSuccessful = true;

    if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
      if (isArrayFull()) {
        doubleArray();
      }
      makeRoom(newPosition);
      array[newPosition - 1] = newEntry;
      numberOfEntries++;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }
    
    public T remove(int givenPosition) {
    T result = null;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      result = array[givenPosition - 1];

      if (givenPosition < numberOfEntries) {
        removeGap(givenPosition);
      }

      numberOfEntries--;
    }

    return result;
  }
    
    public void clear(){
        numberOfEntries = 0;
    }
    
    
    public T getEntry(int givenPosition) {
    T result = null;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      result = array[givenPosition - 1];
    }

    return result;
  }
    
    public boolean contains(T anEntry) {
    boolean found = false;
    for (int index = 0; !found && (index < numberOfEntries); index++) {
      if (anEntry.equals(array[index])) {
        found = true;
      }
    }
    return found;
  }
    
    public int getNumberOfEntries() {
    return numberOfEntries;
  }
    
    public boolean isEmpty() {
    return numberOfEntries == 0;
  }
    
    public boolean isFull() {
    return false;
  }
    
    private void doubleArray() {
    T[] oldArray = array;
    array = (T[]) new Object[oldArray.length * 2];
    for (int i = 0; i < oldArray.length; i++) {
      array[i] = oldArray[i];
    }
  }
    
    private boolean isArrayFull() {
    return numberOfEntries == array.length;
  }
    
    public String toString() {
    String outputStr = "";
    for (int index = 0; index < numberOfEntries; ++index) {
      outputStr += array[index] + "\n";
    }

    return outputStr;
  }
    
    private void makeRoom(int newPosition) {
    int newIndex = newPosition - 1;
    int lastIndex = numberOfEntries - 1;

    
    for (int index = lastIndex; index >= newIndex; index--) {
      array[index + 1] = array[index];
    }
  }
    
    private void removeGap(int givenPosition) {
    
    int removedIndex = givenPosition - 1;
    int lastIndex = numberOfEntries - 1;

    for (int index = removedIndex; index < lastIndex; index++) {
      array[index] = array[index + 1];
    }
  }
  
 
  public void bubbleSort() {
    boolean sorted = false;
        for (int pass = 1; pass < numberOfEntries && !sorted; pass++) {
            sorted = true;
            for (int i = 0; i < numberOfEntries - pass; i++) {
                    if (((Comparable) array[i]).compareTo(array[i + 1]) > 0){
                    swap(i, i + 1);
                    sorted = false;
                }
            } 
        }
    }
    
    private void swap(int first, int second) {
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    // ===== Methods required by ListInterface but missing previously =====
    @Override
    public boolean remove(T item) {
        for (int i = 0; i < numberOfEntries; i++) {
            if ((array[i] == null && item == null) || (array[i] != null && array[i].equals(item))) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public boolean addSorted(T item, Comparator<T> comparator) {
        if (isArrayFull()) {
            doubleArray();
        }
        int idx = lowerBound(item, comparator);
        // shift right from idx
        for (int i = numberOfEntries; i > idx; i--) {
            array[i] = array[i - 1];
        }
        array[idx] = item;
        numberOfEntries++;
        return true;
    }

    @Override
    public int lowerBound(T key, Comparator<T> comparator) {
        int low = 0;
        int high = numberOfEntries;
        while (low < high) {
            int mid = (low + high) >>> 1;
            T midVal = array[mid];
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
        if (index < 0 || index >= numberOfEntries) return;
        for (int i = index; i < numberOfEntries - 1; i++) {
            array[i] = array[i + 1];
        }
        array[numberOfEntries - 1] = null;
        numberOfEntries--;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        for (int i = 0; i < numberOfEntries; i++) {
            for (int j = i + 1; j < numberOfEntries; j++) {
                if (comparator.compare(array[i], array[j]) > 0) {
                    swap(i, j);
                }
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < numberOfEntries;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[cursor++];
            }
        };
    }
}
