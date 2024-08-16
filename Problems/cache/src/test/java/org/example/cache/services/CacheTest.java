package org.example.cache.services;

import org.example.cache.enums.CacheType;
import org.example.cache.factories.CacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Kapil Choudhary
 * @since 16-August-2024
 * Copyright (c) 2024, cache
 * All rights reserved.
 */
public class CacheTest {
    private Cache<Integer, String> cache;

    @BeforeEach
    void setup() {
        cache = CacheFactory.createCache(CacheType.LRU_WITH_HASH_MAP, 3);
    }

    @Test
    void testPutAndGetItemsInTheCache() {
        cache.put(1, "Value1");
        cache.put(2, "Value2");

        assertEquals("Value1", cache.get(1)); // Accessing 1 after 2 got inserted which makes 2 the least recently used till now.
        cache.put(3, "Value3");
        assertEquals("Value3", cache.get(3));

        // Now if I try to add any element, the eviction should happen.
        // Also, eviction should happen based on LRU item which is 2 in this case.
        cache.put(4, "Value4");

        assertNull(cache.get(2));
    }
}
