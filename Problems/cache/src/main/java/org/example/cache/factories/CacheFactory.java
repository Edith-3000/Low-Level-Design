package org.example.cache.factories;

import com.sun.jdi.Value;
import org.example.cache.enums.CacheType;
import org.example.cache.interfaces.evictionpolicies.LRUEvictionPolicy;
import org.example.cache.interfaces.storages.HashMapStorage;
import org.example.cache.services.Cache;

public class CacheFactory<Key, Value> {
    private CacheFactory() {
        throw new UnsupportedOperationException("CacheFactory class cannot be instantiated");
    }

    public static <Key, Value> Cache<Key, Value> createCache(CacheType cacheType, final int capacity) {
        switch (cacheType) {
            case LRU_WITH_HASH_MAP:
                return Cache.getInstance(new LRUEvictionPolicy<Key>(), new HashMapStorage<Key, Value>(capacity));
            default:
                throw new IllegalArgumentException("Unsupported cache type.");
        }
    }
}
