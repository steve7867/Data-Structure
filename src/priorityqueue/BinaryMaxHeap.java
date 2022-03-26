package priorityqueue;

import java.util.NoSuchElementException;

public class BinaryMaxHeap<Key extends Comparable<Key>, Value> {

    private Entry<Key, Value>[] arr;
    private int lastIdx;

    public BinaryMaxHeap(Entry<Key, Value>[] arr, int initialSize) {
        this.arr = arr;
        this.lastIdx = initialSize;
    }

    public int size() {
        return lastIdx;
    }

    public boolean isEmpty() {
        return lastIdx == 0;
    }

    // return whether key of arr[i] is greater than key of arr[j]
    private boolean greater(int i, int j) {
        return arr[i].getKey().compareTo(arr[j].getKey()) > 0;
    }

    private boolean greaterOrEquals(int i, int j) {
        return arr[i].getKey().compareTo(arr[j].getKey()) >= 0;
    }

    private void swap(int i, int j) {
        Entry<Key, Value> temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void resize(int len) {
        Object[] newArr = new Object[len];

        if (lastIdx >= 0)
            System.arraycopy(arr, 1, newArr, 1, lastIdx);

        arr = (Entry<Key, Value>[]) newArr;
    }

    private void downHeap(int i, int n) {
        while (2 * i <= n) {
            int k = 2 * i;
            if (k < n && greater(k + 1, k))
                k++;
            if (greaterOrEquals(i, k))
                break;
            swap(i, k);
            i = k;
        }
    }

    private void recursiveDownHeap(int i, int n) {
        if (2 * i > n)
            return;

        int k = 2 * i;
        if (k < n && greater(k + 1, k))
            k++;

        if (greaterOrEquals(i, k))
            return;

        swap(i, k);

        recursiveDownHeap(k, n);
    }

    private void upHeap(int i) {
        while (i > 1 && greater(i, i / 2)) {
            swap(i, i / 2);
            i /= 2;
        }
    }

    private void recursiveUpHeap(int i) {
        if (i <= 1 || greaterOrEquals(i / 2, i))
            return;

        swap(i, i / 2);
        recursiveUpHeap(i / 2);
    }


    public void createHeap() {
        for (int i = lastIdx / 2; i > 0; i--)
            downHeap(i, lastIdx);
    }

    public Entry<Key, Value> findHighestPriority() {
        if (lastIdx == 0)
            throw new NoSuchElementException();

        return arr[1];
    }

    public void insert(Key newKey, Value newValue) {
        if (lastIdx == arr.length - 1)
            resize(2 * arr.length);

        Entry<Key, Value> temp = new Entry(newKey, newValue);

        arr[++lastIdx] = temp;
        upHeap(lastIdx);
    }

    public Entry<Key, Value> delete() {
        if (isEmpty()) {
            System.err.println("Heap is empty.");
            return null;
        }

        Entry<Key, Value> target = arr[1];
        swap(1, lastIdx);
        arr[lastIdx--] = null;
        downHeap(1, lastIdx);

        if (lastIdx > 0 && lastIdx <= arr.length / 4)
            resize(arr.length / 2);

        return target;
    }

    public void heapSort() {
        createHeap();
        for (int i = lastIdx; i > 1; i--) {
            swap(1, i);
            downHeap(1, i - 1);
        }
    }

}
