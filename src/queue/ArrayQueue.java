package queue;

public class ArrayQueue<E> implements Queue<E> {
    private static final int INIT_CAPACITY = 4;

    private E[] q;
    private int front, rear;
    private int n; // length of q
    private int size; // number of element in q

    public ArrayQueue() {
        q = (E[]) new Object[INIT_CAPACITY];
        front = rear = 0;
        n = q.length;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void add(E item) {
        if (isFull())
            resize(2 * n);

        rear = (rear + 1) % n;
        q[rear] = item;
        size++;
    }

    private boolean isFull() {
        return (rear + 1) % n == front;
    }

    private void resize(int len) {
        if (len < INIT_CAPACITY)
            return;

        E[] temp = (E[]) new Object[len];

        for (int i = 0; i <= size; i++)
            temp[i] = q[(front + i) % n];

        front = 0;
        rear = size;
        q = temp;
        n = q.length;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            System.err.println("Queue is empty.");
            return null;
        }

        return q[(front + 1) % n];
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            System.err.println("Queue is empty.");
            return null;
        }

        front = (front + 1) % n;
        E target = q[front];
        q[front] = null; // Garbage collection
        size--;

        if (size > 0 && size <= n / 4)
            resize(n / 2);

        return target;
    }

    private boolean isEmpty() {
        return front == rear;
    }
}
