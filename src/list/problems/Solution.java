package list.problems;

import list.Node;

public class Solution {
    // https://leetcode.com/problems/reverse-linked-list/
    public Node reverseList(Node head) {
        if (head == null || head.getNext() == null)
            return head;

        Node temp = reverseList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return temp;
    }

    // https://leetcode.com/problems/linked-list-cycle/
    public boolean hasCycle(Node head) {
        if (head == null)
            return false;

        Node walker = head;
        Node runner = head;
        while (runner.getNext() != null && runner.getNext().getNext() != null) {
            walker = walker.getNext();
            runner = runner.getNext().getNext();
            if (walker == runner)
                return true;
        }

        return false;
    }

    public static Node mergeTwoLists(Node l1, Node l2) {
        if (l1 == null && l2 == null)
            return null;

        if (l1 == null)
            return l2;

        if (l2 == null)
            return l1;

        Node head = l1;
        while (l1.getNext() != null)
            l1 = l1.getNext();

        l1.setNext(l2);

        return head;
    }
}
