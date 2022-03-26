package stack;

public class ArrayStack<E> implements Stack<E> {
    private E[] stack;
    private int top;

    public ArrayStack() {
        stack = (E[]) new Object[1];
        top = -1;
    }

    public int getSize() { return top + 1; }
    public boolean isEmpty() { return top == -1; }

    private void resize(int len) {
        Object[] temp = new Object[len];
        for (int i = 0; i <= top; i++)
            temp[i] = stack[i];

        stack = (E[]) temp;
    }

    public E peek() {
        if (isEmpty()) {
            System.err.println("Stack is empty.");
            return null;
        }

        return stack[top];
    }

    public void push(E newItem) {
        if (getSize() == stack.length)
            resize(2 * stack.length);

        stack[++top] = newItem;
    }

    public E pop() {
        if (isEmpty()) {
            System.err.println("Stack is empty.");
            return null;
        }

        E target = stack[top];
        top--;

        if (getSize() > 0 && getSize() == stack.length / 4)
            resize(stack.length / 2);

        return target;
    }

}
