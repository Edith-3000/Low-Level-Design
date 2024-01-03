package org.example.vendingmachine;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.models.ItemRack;
import org.example.vendingmachine.models.ItemType;
import org.example.vendingmachine.services.Inventory;
import org.example.vendingmachine.services.VendingMachine;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Filling up the inventory...\n");

            Inventory inventory = new Inventory();

            inventory.addRack(new ItemRack(101, new Item(ItemType.CHIPS, 10), 0));
            inventory.addRack(new ItemRack(102, new Item(ItemType.COKE, 15), 0));
            inventory.addRack(new ItemRack(103, new Item(ItemType.JUICE, 35), 0));
            inventory.addRack(new ItemRack(104, new Item(ItemType.PEPSI, 60), 0));
            inventory.addRack(new ItemRack(105, new Item(ItemType.WAFER, 20), 0));
            inventory.addRack(new ItemRack(106, new Item(ItemType.WATER, 40), 0));

            inventory.addItemToRack(101);
            inventory.addItemToRack(101);
            inventory.addItemToRack(101);
            inventory.addItemToRack(101);
            inventory.addItemToRack(101);

            inventory.addItemToRack(102);
            inventory.addItemToRack(102);
            inventory.addItemToRack(102);

            inventory.addItemToRack(103);
            inventory.addItemToRack(103);

            inventory.addItemToRack(104);

            inventory.addItemToRack(105);
            inventory.addItemToRack(105);

            inventory.addItemToRack(106);
            inventory.addItemToRack(106);
            inventory.addItemToRack(106);
            inventory.addItemToRack(106);

            inventory.displayInventory();

            VendingMachine vendingMachine = new VendingMachine(inventory);

            vendingMachine.pressInsertMoneyButton();

            vendingMachine.insertMoney(Coin.NICKEL);
            vendingMachine.insertMoney(Coin.QUARTER);

            vendingMachine.pressItemSelectButton();

            vendingMachine.selectItem(102);

            vendingMachine.dispenseItem(102);

            inventory.displayInventory();

            vendingMachine.pressInsertMoneyButton();

            vendingMachine.insertMoney(Coin.DIME);
            vendingMachine.insertMoney(Coin.QUARTER);
            vendingMachine.insertMoney(Coin.NICKEL);

            vendingMachine.pressInsertMoneyButton();

            vendingMachine.pressItemSelectButton();

            vendingMachine.selectItem(104);

            vendingMachine.dispenseItem(104);

            inventory.displayInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
