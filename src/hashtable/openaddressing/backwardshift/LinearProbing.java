package hashtable.openaddressing.backwardshift;

import java.math.BigInteger;

public class LinearProbing<K, V> {
    private static final int INIT_CAPACITY = 13;

    private int capacity, size;
    private Entry<K, V>[] arr;

    public LinearProbing() {
        this(INIT_CAPACITY);
    }

    public LinearProbing(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new Entry[this.capacity];
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(K key, V value) {
        if (key == null) {
            System.err.println("First argument to put() is null");
            return;
        }

        if (value == null) {
            delete(key);
            return;
        }

        int initPos = hash(key);
        int i = initPos;
        do {
            if (arr[i] == null) {
                arr[i] = new Entry<>(key, value);
                size++;
                if (size >= capacity / 2) // load factor < 0.5
                    resize(2 * capacity);
                return;
            }

            if (arr[i].getKey().equals(key)) {
                arr[i].setValue(value);
                return;
            }

            i = (i + 1) % capacity;
        } while (i != initPos);

    }

    // Load factor < 0.5 at least, to make average running time of put(), get(), delete() closer to O(1)
    private void resize(int newCapacity) {
        if (newCapacity < INIT_CAPACITY)
            return;

        BigInteger bi = BigInteger.valueOf(newCapacity);
        newCapacity = bi.nextProbablePrime().intValue();

        LinearProbing<K, V> temp = new LinearProbing<>(newCapacity);
        for (int i = 0; i < this.capacity; i++) {
            if (arr[i] != null)
                temp.put(arr[i].getKey(), arr[i].getValue());
        }

        arr = temp.arr;
        this.capacity = temp.capacity;
        size = temp.size;
    }

    public V get(K key) {
        if (key == null) {
            System.err.println("Argument to get() is null.");
            return null;
        }

        int initPos = hash(key);
        int i = initPos;
        do {
            if (arr[i].getKey().equals(key))
                return arr[i].getValue();

            i = (i + 1) % capacity;

        } while (i != initPos);

        return null;
    }

    public void delete(K key) {
        if (key == null) {
            System.err.println("Argument to delete() is null");
            return;
        }

        int initPos = hash(key);
        int i = initPos;
        do {
            if (arr[i] == null)
                return;

            if (arr[i].getKey().equals(key))
                break;

            i = (i + 1) % capacity;
        } while (i != initPos);

        if (i == initPos)
            return;

        arr[i] = null;
        size--;
        if (size > 0 && size <= capacity / 4) {
            resize(capacity / 2);
            return;
        }

        // Backward shift
        while (true) {
            int j = i;
            while (true) {
                j = (j + 1) % capacity;

                if (arr[j] == null || j == i)
                    return;

                int k = hash(arr[j].getKey());
                if ((k <= i && i < j) || (i < j && j < k) || (j < k && k <= i)) {
                    arr[i] = arr[j];
                    arr[j] = null;
                    i = j;
                    break;
                }
            }
        }
    }

    public boolean contains(K key) {
        if (key == null) {
            System.err.println("Argument to contains() is null.");
            return false;
        }

        return get(key) != null;
    }

    public int getSize() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
}
