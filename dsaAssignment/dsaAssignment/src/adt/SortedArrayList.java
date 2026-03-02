package adt;

import entity.Search;


/*
 * @author CHANG JIA JIN
 */
public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T> {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;

    public SortedArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public SortedArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Comparable[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }

        int i = binarySearch(newEntry);
        if (i < 0) {
            i = -i - 1;
        }

        makeRoom(i + 1);
        array[i] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean remove(T anEntry) {
        int i = binarySearch(anEntry);
        if (i >= 0 && anEntry.equals(array[i])) {
            removeGap(i + 1);
            numberOfEntries--;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        numberOfEntries = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        int i = binarySearch(anEntry);
        return (i >= 0 && anEntry.equals(array[i]));
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; ++index) {
            outputStr += array[index] + "\n";
        }
        return outputStr;
    }

    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;
        array = (T[]) new Comparable[2 * oldSize];
        for (int index = 0; index < oldSize; index++) {
            array[index] = oldList[index];
        }
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

    public boolean isFull() {
        return false;
    }

    @Override
    public Search[] getAllEntries() {
        Search[] jobs = new Search[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            jobs[i] = (Search) array[i];
        }
        return jobs;
    }

    private int binarySearch(T searchEntry) {
        int first = 0;
        int last = numberOfEntries - 1;
        while (first <= last) {
            int mid = (first + last) / 2;
            if (searchEntry.equals(array[mid])) {
                return mid;
            } else if (searchEntry.compareTo(array[mid]) < 0) {
                last = mid - 1;
            } else {
                first = mid + 1;
            }
        }
        return -(first + 1);
    }

    public int fuzzySearch(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    @Override
    public T getEntry(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
