package stack;

public class ListStack<E> implements Stack<E> {
    private Node<E> top;
    private int size;

    public ListStack() {
        top = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    public E peek() {
        if (isEmpty()) {
            System.err.println("Stack is empty.");
            return null;
        }

        return top.getItem();
    }

    public void push(E newItem) {
        top = new Node<>(newItem, top);
        size++;
    }

    public E pop() {
        if (isEmpty()) {
            System.err.println("Stack is empty.");
            return null;
        }

        E target = top.getItem();
        top = top.getNext();
        size--;

        return target;
    }
}
