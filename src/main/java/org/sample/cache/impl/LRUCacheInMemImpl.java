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
        Node node = this.getNode(key);
        if (node != null) {
            node.setValue(value);
            return;
        }
        if (nodeMap.size() == memory) {
            evict();
        }
        node = new Node(key, value);
        putNode(node);
        nodeMap.put(key, node);
    }

    private Node putNode(Node node) {
        if (head == null) {
            tail = node;
        } else {
            head.setPrevious(node);
            node.setNext(head);
        }
        head = node;
        return head;
    }

    @Override
    public Integer remove(String key) {
        Node node = getNode(key);
        if (node == null) {
            return 0;
        }
        this.nodeMap.remove(node.getKey(), node);
        if (this.tail == node || this.head == node) {
            return this.adjustEdges(node);
        }
        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
        return node.getValue();
    }

    private Integer adjustEdges(Node node) {
        if (tail == node && tail == head) {
            head = null;
            tail = null;
            return node.getValue();
        }
        if (tail == node) {
            tail = node.getPrevious();
            tail.setNext(null);
        } else {
            head = node.getNext();
            head.setPrevious(null);
        }
        return node.getValue();
    }

    @Override
    public Integer get(String key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        if (node == head) {
            return node.getValue();
        } else if (node == tail) {
            node.getPrevious().setNext(null);
            tail = node.getPrevious();
        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
        node.setPrevious(null);
        node.setNext(head);
        head.setPrevious(node);
        head = node;
        return node.getValue();
    }

    @Override
    public Integer getSize() {
        return this.nodeMap.size();
    }

    @Override
    public void printCurrentState() {
        Node point = head;
        System.out.print("Current state = ");
        while (point != null) {
            System.out.print("-"+point.getKey());
            point = point.getNext();
        }
        System.out.println();
    }

    private Node getNode(String key) {
        return this.nodeMap.get(key);
    }

    private void evict() {
        String key = tail.getKey();
        nodeMap.remove(key, tail);
        tail.getPrevious().setNext(null);
        tail = tail.getPrevious();
    }
}