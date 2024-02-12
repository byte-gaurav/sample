package org.sample;


import org.sample.cache.LRUCache;
import org.sample.cache.impl.LRUCacheInMemImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        LRUCache lruCache = new LRUCacheInMemImpl(3);
        testLRUCache(lruCache);
    }

    public static void testLRUCache(LRUCache lruCache) {
        lruCache.put("1", 1);
        lruCache.put("2", 2);
        lruCache.put("3", 3);
        lruCache.put("4", 4);
        lruCache.printCurrentState();
        lruCache.remove("2");
        lruCache.get("2");
        lruCache.put("4", 44);
        lruCache.put("1", 1);
        lruCache.remove("2");
        lruCache.printCurrentState();
        lruCache.remove("1");
        lruCache.remove("2");
        lruCache.remove("4");
        lruCache.printCurrentState();
        lruCache.put("1", 1);
        lruCache.printCurrentState();
        lruCache.remove("1");
        lruCache.printCurrentState();
        lruCache.remove("3");
        lruCache.put("1", 1);
        lruCache.put("2", 2);
        lruCache.printCurrentState();
        lruCache.put("3", 3);
        lruCache.put("4", 4);
        lruCache.printCurrentState();
    }
}