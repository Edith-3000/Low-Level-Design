package org.example.vendingmachine.services;

import org.example.vendingmachine.interfaces.IdleVendingMachineState;
import org.example.vendingmachine.interfaces.VendingMachineState;
import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private VendingMachineState vendingMachineState;
    private List<Coin> coins;
    private final Inventory inventory;

    public VendingMachine(Inventory inventory) {
        this.coins = new ArrayList<>();
        this.inventory = inventory;
        this.vendingMachineState = new IdleVendingMachineState(this);
    }

    public void setVendingMachineState(VendingMachineState vendingMachineState) {
        this.vendingMachineState = vendingMachineState;
    }

    public void addCoin(Coin coin) {
        this.coins.add(coin);
    }

    public List<Coin> getCoins() {
        return this.coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void pressInsertMoneyButton() throws Exception {
        this.vendingMachineState.pressInsertMoneyButton();
    }

    public void insertMoney(Coin coin) throws Exception {
        this.vendingMachineState.insertMoney(coin);
    }

    public void pressItemSelectButton() throws Exception {
        this.vendingMachineState.pressItemSelectButton();
    }

    public void selectItem(int codeNumber) throws Exception {
        this.vendingMachineState.selectItem(codeNumber);
    }

    public Item dispenseItem(int codeNumber) throws Exception {
        return this.vendingMachineState.dispenseItem(codeNumber);
    }

//    Should this method be exposed?
//    public void refundExtraMoney(int extraAmountPaid) throws Exception {
//        this.vendingMachineState.refundExtraMoney(extraAmountPaid);
//    }

    public List<Coin> cancelTransactionAndRefund() throws Exception {
        return this.vendingMachineState.cancelTransactionAndRefund();
    }
}
