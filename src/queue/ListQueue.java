package queue;

public class ListQueue<E> implements Queue<E> {
    public static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

        public E getItem() { return item; }
        public void setItem(E item) { this.item = item; }

        public Node<E> getNext() { return next; }
        public void setNext(Node<E> next) { this.next = next; }
    }

    private Node<E> front, rear;
    private int size;

    public ListQueue() {
        front = rear = null;
        size = 0;
    }

    public int getSize() { return size; }
    private boolean isEmpty() { return size == 0; }

    @Override
    public void add(E item) {
        Node<E> newNode = new Node<>(item, null);
        if (isEmpty())
            front = rear = newNode;
        else {
            rear.setNext(newNode);
            rear = rear.getNext();
        }

        size++;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            System.err.println("Queue is empty.");
            return null;
        }

        return front.getItem();
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            System.err.println("Queue is empty.");
            return null;
        }

        Node<E> target = front;
        front = front.getNext();
        size--;

        if (isEmpty())
            rear = null;

        return target.getItem();
    }
}
