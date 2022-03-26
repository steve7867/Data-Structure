package list;

public class CircularLinkedList<E> {
    private Node<E> last;
    private int size;

    public CircularLinkedList() {
        last = null;
        size = 0;
    }

    // Return the node holding the item target. Return null if none.
    public Node<E> search(E target) {
        Node<E> node = last.getNext();
        while (true) {
            if (node.getItem().equals(target))
                return node;

            if (node == last)
                break;

            node = node.getNext();
        }

        return null;
    }

    // Insert the element after the last element
    public void insert(E newItem) {
        Node<E> newNode = new Node<>(newItem, null);
        if (last == null) {
            newNode.setNext(newNode);
            last = newNode;
        } else {
            newNode.setNext(last.getNext());
            last.setNext(newNode);
            last = newNode;
        }

        size++;
    }

    // Delete the first element from the list. Return null if list is empty.
    public Node<E> delete() {
        if (last == null) {
            System.err.println("List is empty.");
            return null;
        }

        Node<E> target = last.getNext();
        if (target == last)
            last = null;
        else {
            last.setNext(target.getNext());
            target.setNext(null);
        }

        size--;

        return target;
    }
}
