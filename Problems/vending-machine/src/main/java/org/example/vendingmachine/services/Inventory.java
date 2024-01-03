package org.example.vendingmachine.services;

import org.example.vendingmachine.constants.Constant;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.models.ItemRack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<ItemRack> itemRacks;

    public Inventory() {
        itemRacks = new ArrayList<>();
    }

    public void addRack(ItemRack itemRack) {
        if (itemRacks.size() + 1 > Constant.RACK_COUNT_THRESHOLD) {
            throw new IllegalArgumentException("Cannot add more racks, threshold is: " + Constant.RACK_COUNT_THRESHOLD);
        }

        itemRacks.add(itemRack);
    }

    public void addItemToRack(int codeNumber) {
        for (ItemRack itemRack: itemRacks) {
            if (itemRack.getCodeNumber() == codeNumber) {
                itemRack.addItem();
            }
        }
    }

    public void removeItemFromRack(int codeNumber) {
        for (ItemRack itemRack: itemRacks) {
            if (itemRack.getCodeNumber() == codeNumber) {
                itemRack.removeItem();
            }
        }
    }

    public Item getItemFromRack(int codeNumber) throws Exception {
        for (ItemRack itemRack: itemRacks) {
            if (itemRack.getCodeNumber() == codeNumber) {
                return itemRack.getItem();
            }
        }

        throw new Exception("No rack found with code: " + codeNumber);
    }

    public void displayInventory() throws Exception {
        for (ItemRack itemRack: this.itemRacks) {
            System.out.println("Rack's code number: " + itemRack.getCodeNumber() +
                    " Item: " + itemRack.getItem().getType() +
                    " Price: " + itemRack.getItem().getPrice() +
                    " Availability count: " + itemRack.getAvailabilityCount());
        }

        if (!itemRacks.isEmpty()) {
            System.out.println();
        }
    }
}
