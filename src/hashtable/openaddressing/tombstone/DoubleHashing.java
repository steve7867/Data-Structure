package hashtable.openaddressing.tombstone;

import java.math.BigInteger;
import java.util.Arrays;

public class DoubleHashing<K, V> {
    private static final int INITIAL_CAPACITY = 13;
    private static final int EMPTY = -1;
    private static final int DUMMY = 0;
    private static final int OCCUPY = 1;

    private int[] status;
    private Entry<K, V>[] arr;
    private int size, capacity;

    public DoubleHashing() {
        this(INITIAL_CAPACITY);
    }

    public DoubleHashing(int capacity) {
        status = new int[capacity];
        arr = new Entry[capacity];
        size = 0;
        this.capacity = capacity;

        Arrays.fill(status, EMPTY);
    }

    public int hashcode(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public int subHash(K key) {
        return 7 - ((key.hashCode() & 0x7fffffff) % 7);
    }

    public int find(K key) {
        int i = hashcode(key);
        int d = subHash(key);

        while (status[i] != EMPTY) {
            if (status[i] == OCCUPY && arr[i].getKey().equals(key))
                return i;

            i = (i + d) % capacity;
        }

        return -1;
    }

    public void put(K key, V value) {
        int i = find(key);

        if (i != -1) {
            arr[i].setValue(value);
            return;
        }

        i = hashcode(key);
        int d = subHash(key);

        while (status[i] == OCCUPY)
            i = (i + d) % capacity;

        status[i] = OCCUPY;
        arr[i] = new Entry<>(key, value);
        size++;

        if (2 * size >= capacity)
            resize(2 * capacity);
    }

    public V get(K key) {
        int i = find(key);

        if (i == -1)
            return null;

        return arr[i].getValue();
    }

    public void delete(K key) {
        int i = find(key);

        if (i == -1)
            return;

        status[i] = DUMMY;
        size--;

        if (4 * size < capacity)
            resize(capacity / 2);
    }

    private void resize(int newCapacity) {
        if (newCapacity < INITIAL_CAPACITY)
            return;

        BigInteger bi = BigInteger.valueOf(newCapacity);
        newCapacity = bi.nextProbablePrime().intValue();

        DoubleHashing<K, V> temp = new DoubleHashing<>(newCapacity);
        for (int i = 0; i < status.length; i++) {
            if (status[i] == OCCUPY)
                temp.put(arr[i].getKey(), arr[i].getValue());
        }

        this.status = temp.status;
        this.arr = temp.arr;
        this.size = temp.size;;
        this.capacity = temp.capacity;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
