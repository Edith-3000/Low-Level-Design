package org.example.vendingmachine.models;

import org.example.vendingmachine.constants.Constant;

public class ItemRack {
    private final int codeNumber;
    private final Item item;
    private int availabilityCount;

    public ItemRack(int codeNumber, Item item, int availabilityCount) {
        this.codeNumber = codeNumber;
        this.item = item;

        if (availabilityCount > Constant.RACK_SIZE_THRESHOLD) {
            throw new IllegalArgumentException("Availability count can't be more than: " + Constant.RACK_SIZE_THRESHOLD);
        }

        this.availabilityCount = availabilityCount;
    }

    public void addItem() {
        if (availabilityCount + 1 > Constant.RACK_SIZE_THRESHOLD) {
            System.out.println("Item rack size threshold reached, can't add more items.\n");
        } else {
            availabilityCount += 1;
        }
    }

    public void removeItem() {
        if (availabilityCount == 0) {
            System.out.println("Item rack already empty.\n");
        } else {
            availabilityCount -= 1;
        }
    }

    public int getCodeNumber() {
        return this.codeNumber;
    }

    public Item getItem() throws Exception {
        if (availabilityCount == 0) {
            throw new Exception("Item rack already empty");
        } else {
            return this.item;
        }
    }

    public int getAvailabilityCount() {
        return this.availabilityCount;
    }
}
