package hashtable.openaddressing.backwardshift;

import java.math.BigInteger;

public class QuadraticProbing<K, V> {
    private static final int INIT_CAPACITY = 13;

    private Entry<K, V>[] arr;
    private int capacity, size;

    public QuadraticProbing() {
        this(INIT_CAPACITY);
    }

    public QuadraticProbing(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new Entry[this.capacity];
    }

    // Return the index for the given key to be by nature
    public int hash(K key) {
        return (key.hashCode() & 0x7ffffff) % capacity;
    }

    // Insert the new entry holding the given key and value into the hashtable
    public void put(K key, V value) {
        if (key == null) {
            System.err.println("First argument to put() is null");
            return;
        }

        if (value == null) {
            delete(key);
            return;
        }

        int i = hash(key), q = 1;
        do {
            if (arr[i] == null) {
                arr[i] = new Entry<>(key, value);
                size++;
                if (size >= capacity / 2)
                    resize(2 * capacity);
                return;
            }

            if (arr[i].getKey().equals(key)) {
                arr[i].setValue(value);
                return;
            }

            i = (i + q * q++) % capacity;
        } while (size < capacity);

    }

    // Extend or reduce the capacity of hashtable
    // Load factor should be 0.5 at least, to make average running time of put(), get(), delete() closer to O(1)
    // To make sure the capacity of hashtable odd, the value of newCapacity should be greater than or equal to INIT_CAPACITY(13)
    private void resize(int newCapacity) {
        if (newCapacity < INIT_CAPACITY)
            return;

        BigInteger bi = BigInteger.valueOf(newCapacity);
        newCapacity = bi.nextProbablePrime().intValue();

        QuadraticProbing<K, V> temp = new QuadraticProbing<>(newCapacity);
        for (int i = 0; i < this.capacity; i++) {
            if (arr[i] == null)
                continue;
            temp.put(arr[i].getKey(), arr[i].getValue());
        }

        this.arr = temp.arr;
        this.capacity = temp.capacity;
        this.size = temp.size;
    }

    // Return the value mapped to the given key if it exists in hashtable, otherwise null
    public V get(K key) {
        if (key == null) {
            System.err.println("Argument to get() is null.");
            return null;
        }

        int i = hash(key), q = 1;
        while (arr[i] != null) {
            if (arr[i].getKey().equals(key))
                return arr[i].getValue();
            i = (i + q * q++) % capacity;
        }

        return null;
    }

    // Delete the entry holding the given key from hashtable
    public void delete(K key) {
        if (key == null) {
            System.err.println("Argument to delete() is null");
            return;
        }

        int i = hash(key), q = 1;
        while (true) {
            if (arr[i] == null)
                return;

            if (arr[i].getKey().equals(key))
                break;

            i = (i + q * q++) % capacity;
        }

        arr[i] = null;
        size--;
        if (size > 0 && size <= capacity / 4) {
            resize(capacity / 2);
            return;
        }

        // Backward shift
        while (true) {
            int j = i;
            q = 1;
            while (true) {
                j = (j + q * q++) % capacity;

                if (arr[j] == null)
                    return;

                if (shiftable(i, j)) {
                    arr[i] = arr[j];
                    arr[j] = null;
                    i = j;
                    break;
                }
            }
        }

    }

    // Determine whether the entry at targetIdx should be shifted to emptyIdx
    private boolean shiftable(int emptyIdx, int targetIdx) {
        int initialPos = hash(arr[targetIdx].getKey());

        int i = initialPos, q = 1;
        int c1 = 0;
        while (i != emptyIdx) {
            i = (i + q * q++) % capacity;
            c1++;
        }

        i = initialPos;
        q = 1;
        int c2 = 0;
        while (i != targetIdx) {
            i = (i + q * q++) % capacity;
            c2++;
        }

        return c1 < c2;
    }

    // Return the number of entry in hashtable
    public int getSize() {
        return size;
    }

    // Determine whether there is no entry in hashtable
    public boolean isEmpty() {
        return size == 0;
    }

    // Determine whether there is entry holding the given key
    public boolean contains(K key) {
        if (key == null) {
            System.err.println("Argument to contains() is null.");
            return false;
        }

        return get(key) != null;
    }

}
