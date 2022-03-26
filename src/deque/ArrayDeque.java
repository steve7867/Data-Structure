package deque;

public class ArrayDeque<E> implements Deque<E> {
    private static final int INIT_CAPACITY = 4;

    private E[] dq;
    private int front, rear;
    private int n; // length of dq array
    private int size; // number of element in array

    public ArrayDeque() {
        this(INIT_CAPACITY);
    }

    public ArrayDeque(int capacity) {
        dq = (E[]) new Object[capacity];
        front = rear = 0;
        n = capacity;
        size = 0;
    }

    public int getSize() { return size; }

    @Override
    public void addFirst(E item) {
        if (isFull())
            resize(2 * n);

        dq[front] = item;
        front = front - 1;
        if (front < 0)
            front = n - 1;
        size++;
    }

    private void resize(int capacity) {
        if (capacity < INIT_CAPACITY)
            return;

        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i <= size; i++)
            temp[i] = dq[(front + i) % n];

        front = 0;
        rear = size;
        n = capacity;
        dq = temp;
    }

    private boolean isFull() {
        return (rear + 1) % n == front;
    }

    @Override
    public void addLast(E item) {
        if (isFull())
            resize(2 * n);

        rear = (rear + 1) % n;
        dq[rear] = item;
        size++;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        return dq[(front + 1) % n];
    }

    private boolean isEmpty() {
        return rear == front;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        return dq[rear];
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        front = (front + 1) % n;
        E target = dq[front];
        dq[front] = null;
        size--;

        if (size > 0 && size <= n / 4)
            resize(n / 2);

        return target;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        E target = dq[rear];
        dq[rear] = null;
        rear = rear - 1;
        if (rear < 0)
            rear = n - 1;
        size--;

        if (size > 0 && size <= n / 4)
            resize(n / 2);

        return target;
    }
}
