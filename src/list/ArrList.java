package list;

public class ArrList<E> {
    public E[] arr;
    public int size;

    @SuppressWarnings("unchecked")
    public ArrList() {
        arr = (E[]) new Object[1];
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    /**
     *
     * @param newSize
     */
    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        Object[] temp = new Object[newSize];

        if (size >= 0)
            System.arraycopy(arr, 0, temp, 0, size);

        arr = (E[]) temp;
    }

    /**
     * @param idx Index of array to be searched
     * @return Item located at idx. If idx is out of boundary, return null.
     */
    public E peek(int idx) {
        if (isOutOfBoundary(idx)) {
            System.err.println("Illegal index: " + idx);
            return null;
        }

        return arr[idx];
    }

    public void insertLast(E newItem) {
        if (isFull())
            resize(2 * arr.length);

        arr[size++] = newItem;
    }

    private boolean isFull() { return size == arr.length; }

    public void insert(E newItem, int k) {
        if (isFull())
            resize(2 * arr.length);

        if (size - k >= 0)
            System.arraycopy(arr, k, arr, k + 1, size - k);
//        for (int i = size - 1; i >= k; i--)
//            arr[i + 1] = arr[i];

        arr[k] = newItem;
        size++;
    }

    public E delete(int idx) {
        if (isOutOfBoundary(idx)) {
            System.err.println("Illegal index: " + idx);
            return null;
        }

        E target = arr[idx];

        if (size - 1 - idx >= 0)
            System.arraycopy(arr, idx + 1, arr, idx, size - 1 - idx);
//        for (int i = idx; i < size - 1; i++)
//            arr[i] = arr[i + 1];

        arr[size - 1] = null;
        size--;

        if (size > 0 && size <= arr.length / 4)
            resize(arr.length / 2);

        return target;
    }

    private boolean isOutOfBoundary(int k) {
        return k < 0 || k >= size;
    }

}
