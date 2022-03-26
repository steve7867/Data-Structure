package queue;

public interface Queue<E> {
    public void add(E item);
    public E peek();
    public E remove();
}
