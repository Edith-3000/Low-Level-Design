package org.example.vendingmachine.models;

public class Item {
    private final ItemType type;
    private final int price;

    public Item(ItemType type, int price) {
        this.type = type;
        this.price = price;
    }

    public ItemType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
