package org.example.cache.interfaces.evictionpolicies;

/**
 * Interface for defining eviction policies.
 *
 * @param <Key> Type of key.
 *
 * @author Kapil Choudhary
 * @version 1.0
 * @since 14-August-2024
 */
public interface IEvictionPolicy<Key> {
    /**
     * Key is accessed, do what is required as per the policy.
     */
    void keyAccessed(Key key);

    /**
     * Evict key from eviction policy and return it.
     */
    Key evict();
}
