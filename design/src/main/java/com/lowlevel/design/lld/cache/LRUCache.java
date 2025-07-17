package org.sample.lld.cache;

public interface LRUCache {
    void put(String key, Integer value);
    Integer remove(String key);

    Integer get(String key);

    Integer getSize();

    void printCurrentState();
}