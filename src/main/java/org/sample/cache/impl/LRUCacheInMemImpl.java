package org.sample.cache.impl;

import org.sample.cache.LRUCache;
import org.sample.cache.dto.Node;

import java.util.HashMap;

public class LRUCacheInMemImpl implements LRUCache {
    private final int memory;
    private Node head;
    private Node tail;
    private final HashMap<String, Node> nodeMap;

    public LRUCacheInMemImpl(int memory) {
        this.memory = memory;
        nodeMap = new HashMap<>(memory);
    }


    @Override
    public void put(String key, Integer value) {
        if (this.nodeMap.size() == this.getSize()) {
            this.evict();
        }
        Node node = this.nodeMap.get(key);
        if (node == null) {
            node = new Node(key, value);
            this.nodeMap.put(node.getKey(), node);
            pushNodeToHead(node, true);
        } else {
            node.setValue(value);
            pushNodeToHead(node, false);
        }
    }

    @Override
    public Integer remove(String key) {
        Node node = this.getNode(key);
        if (node == null) {
            return Integer.MIN_VALUE;
        }
        removeNode(node);
        return node.getValue();
    }

    @Override
    public Integer get(String key) {
        Node node = this.getNode(key);
        if (node == null) {
            return Integer.MIN_VALUE;
        }
        pushNodeToHead(node, false);
        return node.getValue();
    }

    @Override
    public Integer getSize() {
        return this.memory;
    }

    @Override
    public void printCurrentState() {
        if (head == null) {
            System.out.println("No element in cache");
        }
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.getValue() + ", ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    private void removeNode(Node node) {
        if (this.getNode(node.getKey()) == null) {
            return;
        }
        if (this.head == node && this.tail == node) {
            this.head = null;
            this.tail = null;
        } else if (node == tail) {
            this.evict();
        } else if (node == head) {
            head.getNext().setPrevious(null);
            head = head.getNext();
        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
        node.setPrevious(null);
        node.setNext(null);
        this.nodeMap.remove(node.getKey());
    }

    private void evict() {
        if (this.nodeMap.size() < this.getSize()) {
            return;
        }
        if (tail == null) {
            throw new RuntimeException("Eviction called on null tail");
        }
        this.nodeMap.remove(tail.getKey());
        tail.getPrevious().setNext(null);
        tail = tail.getPrevious();
    }

    private void pushNodeToHead(Node node, boolean newNode) {
        if (node == head) {
            return;
        }
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        if (!newNode && node == tail) {
            node.getPrevious().setNext(null);
        } else if (!newNode) {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
        node.setPrevious(null);
        node.setNext(head);
        head.setPrevious(node);
        head = node;
    }

    private Node getNode(String key) {
        return this.nodeMap.get(key);
    }
}