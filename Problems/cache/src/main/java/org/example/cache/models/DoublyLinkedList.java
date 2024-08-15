package org.example.cache.models;

import org.example.cache.exceptions.InvalidElementException;
import org.example.cache.exceptions.NotFoundException;

/**
 * An object which support creating a list with non-contiguous memory allocation. You cannot access a random element
 * directly using index. But if you have a pointer to a node, then you can traverse the list both in forward and
 * backward direction in the list.
 *
 * @param <E> Type of element stored in list.
 */
public class DoublyLinkedList<E> {
    private final DoublyLinkedListNode<E> dummyHead;
    private final DoublyLinkedListNode<E> dummyTail;

    public DoublyLinkedList() {
        dummyHead = new DoublyLinkedListNode<>(null);
        dummyTail = new DoublyLinkedListNode<>(null);

        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    /**
     * Helper method to move a node at the end of the list.
     *
     * @param node Node to be added.
     */
    public void moveNodeAtLast(DoublyLinkedListNode<E> node) {
        node.prev = dummyTail.prev;
        dummyTail.prev.next = node;

        node.next = dummyTail;
        dummyTail.prev = node;
    }

    /**
     * Helper method to add an element at the end.
     *
     * @param element Element to be added.
     * @return Reference to new node created for the element.
     */
    public DoublyLinkedListNode<E> addNewElementAtLast(E element) {
        if (element == null) {
            throw new InvalidElementException("null element passed to add");
        }

        DoublyLinkedListNode<E> dllNode = new DoublyLinkedListNode<>(element);
        moveNodeAtLast(dllNode);

        return dllNode;
    }

    /**
     * Helper method to check if the doubly linked list has no elements.
     *
     * @return true if no elements, otherwise false.
     */
    private boolean noNodePresent() {
        return (dummyHead.next == dummyTail);
    }

    /**
     * Method to get the first element of the doubly linked list.
     *
     * @return Reference to first node of the doubly linked list.
     */
    public DoublyLinkedListNode<E> getFirstNode() {
        if (noNodePresent()) {
            return null;
        }

        return dummyHead.next;
    }

    /**
     * Method to get the last element of the doubly linked list.
     *
     * @return Reference to last node of the doubly linked list.
     */
    public DoublyLinkedListNode<E> getLastNode() {
        if (noNodePresent()) {
            return null;
        }

        return dummyTail.prev;
    }

    /**
     * Method to detach a random node from the doubly linked list. The node itself will not be removed from the memory.
     * Just that it will be removed from the list and becomes orphaned.
     *
     * @param node Node to be detached.
     */
    public void detachNode(DoublyLinkedListNode<E> node) {
        if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
