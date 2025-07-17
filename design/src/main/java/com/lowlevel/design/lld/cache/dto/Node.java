package org.sample.lld.cache.dto;

public class Node {

    private String key;

    private int value;

    private Node previous;

    private Node next;

    private Node() { }

    public Node(String key, int value) {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
