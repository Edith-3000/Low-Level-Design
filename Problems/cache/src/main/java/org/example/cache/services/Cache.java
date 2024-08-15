package org.example.cache.services;

import org.example.cache.exceptions.NotFoundException;
import org.example.cache.exceptions.StorageFullException;
import org.example.cache.interfaces.evictionpolicies.IEvictionPolicy;
import org.example.cache.interfaces.storages.IStorage;

import java.util.concurrent.locks.ReentrantLock;

public class Cache<Key, Value> {
    private static volatile Cache<?, ?> instance;

    private final IEvictionPolicy<Key> evictionPolicy;
    private final IStorage<Key, Value> storage;

    private final ReentrantLock lock;

    private Cache(IEvictionPolicy<Key> evictionPolicy, IStorage<Key, Value> storage) {
        this.evictionPolicy = evictionPolicy;
        this.storage = storage;
        lock = new ReentrantLock();
    }

    public static <Key, Value> Cache<Key, Value> getInstance(IEvictionPolicy<Key> evictionPolicy, IStorage<Key, Value> storage) {
        if (instance == null) {
            synchronized (Cache.class) {
                if (instance == null) {
                    instance = new Cache<>(evictionPolicy, storage);
                }
            }
        }

        return (Cache<Key, Value>) instance;
    }

    public void put(Key key, Value value) {
        lock.lock();

        try {
            storage.add(key, value);
            evictionPolicy.keyAccessed(key);
        } catch (StorageFullException e) {
            System.out.println("Storage full, will try to evict item.");

            Key keyToRemove = evictionPolicy.evict();
            if (keyToRemove == null) {
                throw new RuntimeException("Unexpected state. Storage full and no key to evict");
            }

            System.out.println("Creating space by evicting item..." + keyToRemove);
            storage.remove(keyToRemove);

            // If you use a ReentrantLock, the same thread can lock the lock again without any problem, so
            // recursion will work fine here.
            put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public Value get(Key key) {
        lock.lock();

        try {
            Value value = storage.get(key);
            evictionPolicy.keyAccessed(key);
            return value;
        } catch (NotFoundException e) {
            System.out.println("Key does not exist.");
            return null;
        } finally {
            lock.unlock();
        }
    }
}
