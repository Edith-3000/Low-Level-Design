package org.example.cache.interfaces.storages;

import org.example.cache.exceptions.NotFoundException;
import org.example.cache.exceptions.StorageFullException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HashMapStorage<Key, Value> implements IStorage<Key, Value> {
    private final int capacity;
    private final Map<Key, Value> storage;
    private final ReadWriteLock lock;

    public HashMapStorage(int capacity) {
        this.capacity = capacity;
        this.storage = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    public int getCapacity() {
        return capacity;
    }

    private boolean storageFull() {
        return capacity == storage.size();
    }

    @Override
    public void add(Key key, Value value) {
        lock.writeLock().lock();
        try {
            if (storageFull()) {
                throw new StorageFullException("Storage capacity full.");
            }

            storage.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void remove(Key key) throws NotFoundException {
        lock.writeLock().lock();
        try {
            if (!storage.containsKey(key)) {
                throw new NotFoundException(key + " not found in cache.");
            }

            storage.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Value get(Key key) throws NotFoundException {
        lock.readLock().lock();
        try {
            if (!storage.containsKey(key)) {
                throw new NotFoundException(key + " not found in cache");
            }

            return storage.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}
