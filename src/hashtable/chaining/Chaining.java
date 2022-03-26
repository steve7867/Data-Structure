package hashtable.chaining;

public class Chaining<K, V> {
    public static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() { return key; }
        public void setKey(K key) { this.key = key; }

        public V getValue() { return value; }
        public void setValue(V value) { this.value = value; }

        public Node<K, V> getNext() { return next; }
        public void setNext(Node<K, V> next) { this.next = next; }
    }

    private static final int INIT_CAPACITY = 13;

    private Node<K, V>[] arr;
    private int capacity, size;

    public Chaining() {
        this(INIT_CAPACITY);
    }

    public Chaining(int capacity) {
        arr = new Node[capacity];
        size = 0;
        this.capacity = capacity;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(K key, V value) {
        if (key == null) {
            System.err.println("First argument to put() is null");
            return;
        }

        int i = hash(key);
        insert(i, key, value);
    }

    private void insert(int i, K key, V value) {
        for (Node<K, V> x = arr[i]; x != null; x = x.next) {
            if (x.getKey().equals(key)) {
                x.setValue(value);
                return;
            }
        }

        arr[i] = new Node<>(key, value, arr[i]);
        size++;

        if (size >= 10 * capacity)
            resize(2 * capacity);
    }

    // Load factor of 10 is ideal to make average running time of put(), get(), delete() closer to O(1)
    // To make sure the capacity of hashtable odd, the value of newCapacity should be greater than or equal to INIT_CAPACITY(13)
    private void resize(int newCapacity) {
        if (newCapacity < INIT_CAPACITY)
            return;

        Chaining<K, V> temp = new Chaining<>(newCapacity);
        for (int i = 0; i < capacity; i++) {
            for (Node<K, V> x = arr[i]; x != null ; x = x.getNext())
                temp.put(x.getKey(), x.getValue());
        }

        this.arr = temp.arr;
        this.capacity = temp.capacity;
        this.size = temp.size;
    }

    public V get(K key) {
        if (key == null) {
            System.err.println("Argument to get() is null.");
            return null;
        }

        int i = hash(key);
        return search(arr[i], key);
    }

    private V search(Node<K, V> cur, K key) {
        while (cur != null) {
            if (cur.getKey().equals(key))
                return cur.getValue();

            cur = cur.getNext();
        }

        return null;
    }

    public void delete(K key) {
        if (key == null) {
            System.err.println("Argument to delete() is null");
            return;
        }

        int i = hash(key);
        remove(i, key);
    }

     private void remove(int i, K key) {
        if (arr[i] == null)
            return;

        if (arr[i].getKey().equals(key)) {
            arr[i] = arr[i].getNext();
            size--;
            return;
        }

         for (Node<K, V> x = arr[i]; x.getNext() != null; x = x.getNext()) {
             if (x.getNext().getKey().equals(key)) {
                 x.setNext(x.getNext().getNext());
                 size--;
                 return;
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
