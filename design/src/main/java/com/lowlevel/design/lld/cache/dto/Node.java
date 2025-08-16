package org.sample.lld.cache.dto;

public class Node {

    private int key;

    private int value;

    private Node previous;

    private Node next;

    private Node() { }

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Node obj = (Node) o;
        return (obj.key+"_"+obj.value).equals(this.key+"_"+this.value);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
