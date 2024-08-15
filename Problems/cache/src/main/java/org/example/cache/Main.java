package org.example.cache;

import org.example.cache.enums.CacheType;
import org.example.cache.factories.CacheFactory;
import org.example.cache.services.Cache;

import java.security.Key;
import java.util.Random;

public class Main {
    // CacheWorker is dummy class to test working of multithreaded cache
    static class CacheWorker implements Runnable {
        private final Cache<Integer, String> cache;
        private final int threadId;
        private final Random random;

        CacheWorker(Cache<Integer, String> cache, int threadId) {
            this.cache = cache;
            this.threadId = threadId;
            this.random = new Random();
        }

        @Override
        public void run() {
            // Perform cache operations
            for (int i = 0; i < 10; i++) {
                int key = random.nextInt(10); // Random key between 0 and 9
                System.out.print("Random key generated: " + key + " ************ ");
                String value = "Value" + key;

                // 50% chance to put or get
                if (random.nextBoolean()) {
                    System.out.println("Thread " + threadId + " putting: " + key + " -> " + value);
                    cache.put(key, value);
                } else {
                    String result = cache.get(key);
                    System.out.println("Thread " + threadId + " getting: " + key + " -> " + result);
                }

                // Sleep for a random time between 0 and 100 milliseconds
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Cache<Integer, String> cache = CacheFactory.createCache(CacheType.LRU_WITH_HASH_MAP, 5);

//        cache.put(0, "Value0");
//        cache.get(0);
//        cache.put(1, "Value1");
//        cache.put(2, "Value2");
//        cache.get(2);
//        cache.put(3, "Value3");
//        cache.put(4, "Value4");
//        cache.put(4, "Value4");
//        cache.put(5, "Value5");
//        cache.get(0);
//        cache.put(6, "Value6");
//        cache.get(3);
//        cache.put(7, "Value7");

        int numThreads = 200;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new CacheWorker(cache, i));
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
