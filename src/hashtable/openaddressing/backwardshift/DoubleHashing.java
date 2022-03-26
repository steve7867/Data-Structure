package hashtable.openaddressing.backwardshift;

import java.math.BigInteger;

public class DoubleHashing<K, V> {
    private static final int INIT_CAPACITY = 13;

    private Entry<K, V>[] arr;
    private int capacity, size;

    public DoubleHashing() {
        this(INIT_CAPACITY);
    }

    public DoubleHashing(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new Entry[capacity];
    }

    // Return the index for the given key to be by nature
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    // Return the size of jump sequence
    private int subHash(K key) {
        return 7 - ((key.hashCode() & 0x7fffffff) % 7);
        //        return (7 - ((int) key % 7));
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

        int i = hash(key);
        int d = subHash(key);
        while (true) {
            if (arr[i] == null) {
                arr[i] = new Entry<>(key, value);
                size++;
                if (size >= capacity / 2) // load factor is larger than 0.5
                    resize(2 * capacity);
                return;
            }

            if (arr[i].getKey().equals(key)) {
                arr[i].setValue(value);
                return;
            }

            i = (i + d) % capacity;
        }

    }

    // Extend or reduce the capacity of hashtable
    // Load factor should be 0.5 at least, to make average running time of put(), get(), delete() closer to O(1)
    // To make sure the capacity of hashtable odd, the value of newCapacity should be greater than or equal to INIT_CAPACITY(13)
    private void resize(int newCapacity) {
        if (newCapacity < INIT_CAPACITY)
            return;

        // table size should be odd(prime, better) to avoid circulation(which occurs when both jump sequence and table size even)
        BigInteger bi = BigInteger.valueOf(newCapacity);
        newCapacity = bi.nextProbablePrime().intValue();

        DoubleHashing<K, V> temp = new DoubleHashing<>(newCapacity);
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

        int i = hash(key);
        int d = subHash(key);

        while (arr[i] != null) {
            if (arr[i].getKey().equals(key))
                return arr[i].getValue();

            i = (i + d) % capacity;
        }

        return null;
    }

    // Delete the entry holding the given key from hashtable
    public void delete(K key) {
        if (key == null) {
            System.err.println("Argument to delete() is null");
            return;
        }

        int i = hash(key);
        int d = subHash(key);
        while (true) {
            if (arr[i] == null)
                return;

            if (arr[i].getKey().equals(key))
                break;

            i = (i + d) % capacity;
        }

        arr[i] = null;
        size--;
        if (size > 0 && size <= capacity / 4) {
            resize(capacity / 2);
            return;
        }

        // Backward shift
        while (true) {
            boolean goOn = false;
            for (d = 1; d <= 7; d++) {
                int j = (i + d) % capacity;

                if (arr[j] == null)
                    continue;

                if (shiftable(i, j)) {
                    arr[i] = arr[j];
                    arr[j] = null;
                    i = j;
                    goOn = true;
                    break;
                }
            }

            if (goOn)
                continue;

            return;
        }
    }

    // Determine whether the entry at targetIdx should be shifted to emptyIdx
    private boolean shiftable(int emptyIdx, int targetIdx) {
        int initialPos = hash(arr[targetIdx].getKey());
        int d = subHash(arr[targetIdx].getKey());

        int i = initialPos;
        int c1 = 0;
        while (i != emptyIdx) {
            i = (i + d) % capacity;
            c1++;
        }

        i = initialPos;
        int c2 = 0;
        while (i != targetIdx) {
            i = (i + d) % capacity;
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
