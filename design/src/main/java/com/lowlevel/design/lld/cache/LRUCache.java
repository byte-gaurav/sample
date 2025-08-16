package org.sample.lld.cache;

public interface LRUCache {
    void put(int key, int value);
    Integer remove(int key);

    Integer get(int key);

    Integer getSize();

    void printCurrentState();
}