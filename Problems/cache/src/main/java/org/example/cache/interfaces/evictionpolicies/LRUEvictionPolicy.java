package org.example.cache.interfaces.evictionpolicies;

import org.example.cache.models.DoublyLinkedList;
import org.example.cache.models.DoublyLinkedListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Eviction policy based on LRU algorithm. This policy will always keep the
 * least recently used key at the end of doubly linked list and the key to be
 * evicted at the front.
 *
 * @param <Key> Key type.
 */
public class LRUEvictionPolicy<Key> implements IEvictionPolicy<Key> {
    private final DoublyLinkedList<Key> dll;
    private final Map<Key, DoublyLinkedListNode<Key>> mapper;

    public LRUEvictionPolicy() {
        dll = new DoublyLinkedList<>();
        mapper = new HashMap<>();
    }

    @Override
    public void keyAccessed(Key key) {
        if (!mapper.containsKey(key)) {
            DoublyLinkedListNode<Key> node = dll.addNewElementAtLast(key);
            mapper.put(key, node);
        } else {
            DoublyLinkedListNode<Key> node = mapper.get(key);

            dll.detachNode(node);
            dll.moveNodeAtLast(node);
        }
    }

    @Override
    public Key evict() {
        DoublyLinkedListNode<Key> node = dll.getFirstNode();
        if (node == null) {
            return null;
        }

        dll.detachNode(node);
        return node.getElement();
    }
}
