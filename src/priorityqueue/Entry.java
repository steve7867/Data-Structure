package priorityqueue;

public class Entry <Key extends Comparable<Key>, Value> {
    private Key key;
    private Value val;

    public Entry(Key key, Value val) {
        this.key = key;
        this.val = val;
    }

    public Key getKey() { return key; }
    public Value getVal() { return val; }

    public void setKey(Key key) { this.key = key; }
    public void setVal(Value val) { this.val = val; }
}
