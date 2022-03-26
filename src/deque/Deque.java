package deque;

public interface Deque<E> {
    public void addFirst(E item);
    public void addLast(E item);
    public E peekFirst();
    public E peekLast();
    public E removeFirst();
    public E removeLast();
}
