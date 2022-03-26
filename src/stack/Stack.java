package stack;

public interface Stack<E> {
    public abstract int getSize();
    public abstract boolean isEmpty();
    public abstract E peek();
    public abstract void push(E newItem);
    public abstract E pop();
}
