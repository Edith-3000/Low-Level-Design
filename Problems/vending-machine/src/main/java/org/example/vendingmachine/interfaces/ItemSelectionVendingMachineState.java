package org.example.vendingmachine.interfaces;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.services.VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectionVendingMachineState implements VendingMachineState {
    private final VendingMachine vendingMachine;

    public ItemSelectionVendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        System.out.println("Vending machine's state: ITEM SELECTION.\n");
    }

    @Override
    public void pressInsertMoneyButton() throws Exception {
        throw new Exception("Cannot use insert money button in ITEM SELECTION state");
    }

    @Override
    public void insertMoney(Coin coin) throws Exception {
        throw new Exception("Cannot insert coins in ITEM SELECTION state");
    }

    @Override
    public void pressItemSelectButton() {}

    @Override
    public void selectItem(int codeNumber) throws Exception {
        Item item = this.vendingMachine.getInventory().getItemFromRack(codeNumber);

        int amountPaidByUser = 0;
        for (Coin coin: this.vendingMachine.getCoins()) {
            amountPaidByUser += coin.getValue();
        }

        if (amountPaidByUser < item.getPrice()) {
            System.out.println("Insufficient amount to buy " + item.getType() + " item's price is: " + item.getPrice() + " and you paid: " + amountPaidByUser + "\n");
            cancelTransactionAndRefund();
            this.vendingMachine.setVendingMachineState(new IdleVendingMachineState(this.vendingMachine));
            throw new Exception("Insufficient amount to buy item: " + item.getType());
        } else {
            if (amountPaidByUser > item.getPrice()) {
                refundExtraMoney(amountPaidByUser - item.getPrice());
            }

            this.vendingMachine.setCoins(new ArrayList<>());
            this.vendingMachine.setVendingMachineState(new ItemDispenseVendingMachineState(this.vendingMachine));
        }
    }

    @Override
    public Item dispenseItem(int codeNumber) throws Exception {
        throw new Exception("Cannot dispense item in ITEM SELECTION state");
    }

    @Override
    public void refundExtraMoney(int extraAmountPaid) {
        // Actual logic should be to return COINs in the dispense tray, but for simplicity doing a print statement
        System.out.println("Please collect the extra amount of " + extraAmountPaid + " from coin dispense tray.\n");
    }

    @Override
    public List<Coin> cancelTransactionAndRefund() {
        System.out.println("Please collect the refund amount from coin dispense tray.\n");
        this.vendingMachine.setVendingMachineState(new IdleVendingMachineState(this.vendingMachine));
        List<Coin> coins = this.vendingMachine.getCoins();
        this.vendingMachine.setCoins(new ArrayList<>());
        return coins;
    }
}
