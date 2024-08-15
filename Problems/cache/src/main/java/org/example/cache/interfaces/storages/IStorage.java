package org.example.cache.interfaces.storages;

import org.example.cache.exceptions.NotFoundException;
import org.example.cache.exceptions.StorageFullException;

public interface IStorage<Key, Value> {
    void add(Key key, Value value) throws StorageFullException;
    void remove(Key key) throws NotFoundException;
    Value get(Key key) throws NotFoundException;
}
