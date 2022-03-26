package list;

public class DoublyLinkedList<E> {
    private DNode<E> head, tail;
    private int size;

    public DoublyLinkedList() {
        head = new DNode<>(null, null, null);
        tail = new DNode<>(null, head, null);
        head.setNext(tail);
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Return the node with the given item target. Return null if none.
    public DNode<E> search(E target) {
        DNode<E> node = head.getNext();
        while (node != tail) {
            if (node.getItem().equals(target))
                return node;
            node = node.getNext();
        }

        return null;
    }

    // Insert new node before the node p. Return true if insertion is made, otherwise false.
    public boolean insertBefore(DNode<E> p, E newItem) {
        if (p == null) {
            System.err.println("First argument to insertBefore() is null.");
            return false;
        }

        DNode<E> t = p.getPrevious();
        t.setNext(new DNode<>(newItem, t, p));
        p.setPrevious(t.getNext());
        size++;

        return true;
    }

    // Insert new node after the node p. Return true if insertion is made, otherwise false.
    public boolean insertAfter(DNode<E> p, E newItem) throws IllegalArgumentException {
        if (p == null) {
            System.err.println("first argument to insertAfter() is null");
            return false;
        }

        DNode<E> t = p.getNext();
        p.setNext(new DNode<>(newItem, p, t));
        t.setPrevious(p.getNext());
        size++;

        return true;
    }

    // Delete the node x from list. Return true if deletion is made, otherwise null.
    public boolean delete(DNode<E> x) throws IllegalArgumentException {
        if (x == null) {
            System.err.println("Argument to delete() is null.");
            return false;
        }

        if (x == head || x == tail) {
            System.err.println("Dummy node can't be removed.");
            return false;
        }

        x.getPrevious().setNext(x.getNext());
        x.getNext().setPrevious(x.getPrevious());
        size--;

        return true;
    }
}
