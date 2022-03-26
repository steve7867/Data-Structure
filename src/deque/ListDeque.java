package deque;

public class ListDeque<E> implements Deque<E> {
    public static class Node<E> {
        private E item;
        private Node<E> prev, next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public E getItem() { return item; }
        public void setItem(E item) { this.item = item; }

        public Node<E> getPrev() { return prev; }
        public void setPrev(Node<E> prev) { this.prev = prev; }

        public Node<E> getNext() { return next; }
        public void setNext(Node<E> next) { this.next = next; }
    }

    private Node<E> head, tail;
    private int size;

    public ListDeque() {
        head = new Node<E>(null, null, null); // dummy node
        tail = new Node<E>(null, head, null); // dummy node
        head.setNext(tail);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void addFirst(E item) {
        Node<E> newNode = new Node<E>(item, head, head.getNext());
        head.setNext(newNode);
        newNode.getNext().setPrev(newNode);
        size++;
    }

    @Override
    public void addLast(E item) {
        Node<E> newNode = new Node<E>(item, tail.getPrev(), tail);
        tail.setPrev(newNode);
        newNode.getPrev().setNext(newNode);
        size++;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        return head.getNext().getItem();
    }

    private boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        return tail.getPrev().getItem();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        E target = head.getNext().getItem();
        head.setNext(head.getNext().getNext());
        head.getNext().setPrev(head);
        size--;

        return target;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            System.err.println("Deque is empty.");
            return null;
        }

        E target = tail.getPrev().getItem();
        tail.setPrev(tail.getPrev().getPrev());
        tail.getPrev().setNext(tail);
        size--;

        return target;
    }
}
