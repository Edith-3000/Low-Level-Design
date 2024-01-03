package org.example.vendingmachine.interfaces;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.services.VendingMachine;

import java.util.List;

public class ItemDispenseVendingMachineState implements VendingMachineState {
    private final VendingMachine vendingMachine;

    public ItemDispenseVendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        System.out.println("Vending machine's state: ITEM DISPENSE.\n");
    }

    @Override
    public void pressInsertMoneyButton() throws Exception {
        throw new Exception("Cannot use insert money button in ITEM DISPENSE state");
    }

    @Override
    public void insertMoney(Coin coin) throws Exception {
        throw new Exception("Cannot insert coins in ITEM DISPENSE state");
    }

    @Override
    public void pressItemSelectButton() throws Exception {
        throw new Exception("Cannot use insert money button in ITEM DISPENSE state");
    }

    @Override
    public void selectItem(int codeNumber) throws Exception {
        throw new Exception("Cannot select item in ITEM DISPENSE state");
    }

    @Override
    public Item dispenseItem(int codeNumber) throws Exception {
        Item item = this.vendingMachine.getInventory().getItemFromRack(codeNumber);
        System.out.println("Dispensing item: " + item.getType() + "\n");
        this.vendingMachine.getInventory().removeItemFromRack(codeNumber);
        this.vendingMachine.setVendingMachineState(new IdleVendingMachineState(this.vendingMachine));
        return item;
    }

    @Override
    public void refundExtraMoney(int extraAmountPaid) throws Exception {
        throw new Exception("Cannot refund in ITEM DISPENSE state");
    }

    @Override
    public List<Coin> cancelTransactionAndRefund() throws Exception {
        throw new Exception("Cannot cancel transaction and refund in ITEM DISPENSE state");
    }
}
