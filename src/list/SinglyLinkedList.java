package list;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Return the index at which the given item target is located. Return -1 if none.
    public int searchIndex(E target) {
        Node<E> node = head;
        int i = 0;
        while (node != null) {
            if (node.getItem().equals(target))
                return i;

            node = node.getNext();
            i++;
        }

        return -1;
    }

    // Return the node in which the given item is stored. Return null if none.
    public Node<E> searchNode(E target) {
        Node<E> node = head;
        while (node != null) {
            if (node.getItem().equals(target))
                return node;

            node = node.getNext();
        }

        return null;
    }

    // Insert node at the front of list. Return true if insertion is made.
    public boolean insertFront(E newItem) {
        Node<E> newNode = new Node<>(newItem, null);
        if (isEmpty())
            head = newNode;
        else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;

        return true;
    }

    // Insert node holding newItem after the node pre. Return true is insertion is made, otherwise false.
    public boolean insertAfter(E newItem, Node<E> pre) {
        if (pre == null) {
            System.err.println("Second argument to insertAfter() is null");
            return false;
        }

        pre.setNext(new Node<>(newItem, pre.getNext()));
        size++;

        return true;
    }

    // Delete and return node at the front of list. If list is empty, return null.
    public Node<E> deleteFront() {
        if (isEmpty()) {
            System.err.println("List is empty.");
            return null;
        }

        Node<E> target = head;
        head = head.getNext();
        size--;

        return target;
    }

    // Delete and return the node after the node pre. If deletion is failed, return null.
    public Node<E> deleteAfter(Node<E> pre) {
        if (pre == null) {
            System.err.println("Argument to deleteAfter() is null.");
            return null;
        }

        if (pre.getNext() == null) {
            System.err.println("There is none after the given node.");
            return null;
        }

        Node<E> target = pre.getNext();
        pre.setNext(target.getNext());
        size--;

        return target;
    }

}
