package binarysearchtree;

import java.util.*;

public class BinarySearchTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(K key, V value) {
        this.root = new Node<>(key, value);
    }

    public BinarySearchTree(K[] keys) {
        Arrays.sort(keys);
        root = ConvertSortedArrayToBST(keys, 0, keys.length - 1);
    }

    // https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
    private Node<K, V> ConvertSortedArrayToBST(K[] keys, int l, int r) {
        if (l > r)
            return null;

        int mid = (l + r) / 2;

        Node<K, V> root = new Node<K, V>(keys[mid], null);
        root.setLeft(ConvertSortedArrayToBST(keys, l, mid - 1));
        root.setRight(ConvertSortedArrayToBST(keys, mid + 1, r));

        return root;
    }

    // https://leetcode.com/problems/validate-binary-search-tree/
    public boolean isValidBST() {
        return isValidBST(root);
    }

    private boolean isValidBST(Node<K, V> root) {
        if (root == null)
            return true;

        Stack<Node<K, V>> stack = new Stack<>();
        Node<K, V> pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            root = stack.pop();
            if (pre != null && root.getKey().compareTo(pre.getKey()) <= 0)
                return false;

            pre = root;
            root = root.getRight();
        }

        return true;
    }

    private boolean isEmpty() { return root == null; }
    public int getSize() {
        return getSize(root);
    }

    // O(n), postorder
    // return the number of nodes in binarysearchtree which have the given 'node' as root
    private int getSize(Node<K, V> node) {
        if (node == null)
            return 0;

        return 1 + getSize(node.getLeft()) + getSize(node.getRight());
    }

    public int getHeight() {
        return getHeight(root);
    }

    // O(n), postorder
    // return the height of binarysearchtree which have the given 'node' as root
    private int getHeight(Node<K, V> node) {
        if (node == null)
            return 0;

        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    public void preorder() {
        preorder(root);
    }

    // O(n)
    // traverse node -> left subtree -> right subtree
    private void preorder(Node<K, V> node) {
        if (node == null)
            return;

        System.out.println(node.getKey());
        preorder(node.getLeft());
        preorder(node.getRight());
    }

    public void inorder() {
        inorder(root);
    }

    // O(n)
    // traverse left subtree -> node -> right subtree
    private void inorder(Node<K, V> node) {
        if (node == null)
            return;

        inorder(node.getLeft());
        System.out.println(node.getKey());
        inorder(node.getRight());
    }

    public void postorder() {
        postorder(root);
    }

    // O(n)
    // traverse left subtree -> right subtree -> node
    private void postorder(Node<K, V> node) {
        if (node == null)
            return;

        postorder(node.getLeft());
        postorder(node.getRight());
        System.out.println(node.getKey());
    }

    public void levelorder() {
        levelorder(root);
    }

    // O(n)
    // traverse all node from the first level to the last level and from left to right
    private void levelorder(Node<K, V> node) {
        if (node == null)
            return;
        Queue<Node<K, V>> q = new LinkedList<>();
        q.offer(node);
        while (!q.isEmpty()) {
            node = q.poll();
            System.out.println(node.getKey());
            if (node.getLeft() != null)
                q.offer(node.getLeft());
            if (node.getRight() != null)
                q.offer(node.getRight());
        }
    }

    public V get(K key) {
        return get(root, key);
    }

    // O(h) (h is height of binarysearchtree)
    // return the value of node holding the given 'key' in the subtree which have 'node' as root node
    private V get(Node<K, V> node, K key) {
        if (node == null)
            return null;

        int t = node.getKey().compareTo(key);
        if (t < 0)
            return get(node.getRight(), key);
        else if (t > 0)
            return get(node.getLeft(), key);
        else
            return node.getValue();
    }

    // O(h)
    private V iterativeGet(Node<K, V> node, K key) {
        while (node != null) {
            int t = node.getKey().compareTo(key);
            if (t < 0)
                node = node.getRight();
            else if (t > 0)
                node = node.getLeft();
            else
                return node.getValue();
        }
        return null;
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    // O(h)
    // insert the new node into the tree which have the given 'node' as root and return the root
    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null)
            return new Node<>(key, value);

        int t = node.getKey().compareTo(key);
        if (t < 0)
            node.setRight(put(node.getRight(), key, value));
        else if (t > 0)
            node.setLeft(put(node.getLeft(), key, value));
        else
            node.setValue(value);

        return node;
    }

    public Node<K, V> getMin() {
        return getMin(root);
    }

    // O(h)
    // return the reference to node holding the minimum key
    private Node<K, V> getMin(Node<K, V> node) {
        if (node.getLeft() == null)
            return node;

        return getMin(node.getLeft());
    }

    public void deleteMin() {
        if (root == null)
            return;

        root = deleteMin(root);
    }

    // O(h)
    // delete the node holding the minimum key in the tree which have the given 'node' as root and return the root node
    private Node<K, V> deleteMin(Node<K, V> node) {
        if (node.getLeft() == null)
            return node.getRight();

        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }

    public void delete(K key) {
        if (isEmpty()) {
            System.err.println("Tree is empty.");
            return;
        }

        root = delete(root, key);
    }

    // O(h)
    // delete the node holding the given 'key' in the tree which have the given 'node' as root and return the root node
    private Node<K, V> delete(Node<K, V> node, K key) {
        if (node == null)
            return null;

        int t = node.getKey().compareTo(key);

        if (t > 0)
            node.setLeft(delete(node.getLeft(), key));
        else if (t < 0)
            node.setRight(delete(node.getRight(), key));
        else {
            if (node.getLeft() == null)
                return node.getRight();
            if (node.getRight() == null)
                return node.getLeft();

            Node<K, V> target = node;
            node = getMin(target.getRight());
            node.setRight(deleteMin(target.getRight()));
            node.setLeft(target.getLeft());
        }

        return node;
    }

    // https://leetcode.com/problems/same-tree/solution/
    // O(n), preorder
    public boolean isEqual(Node<K, V> n1, Node<K, V> n2) {
        if (n1 == null || n2 == null)
            return n1 == n2;

        if (n1.getKey().compareTo(n2.getKey()) != 0)
            return false;

        return isEqual(n1.getLeft(), n2.getLeft()) && isEqual(n1.getRight(), n2.getRight());
    }
}
