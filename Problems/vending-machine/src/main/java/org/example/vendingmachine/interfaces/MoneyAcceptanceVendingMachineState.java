package org.example.vendingmachine.interfaces;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.services.VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class MoneyAcceptanceVendingMachineState implements VendingMachineState {
    private final VendingMachine vendingMachine;

    public MoneyAcceptanceVendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        System.out.println("Vending machine's state: MONEY ACCEPTANCE.\n");
    }

    @Override
    public void pressInsertMoneyButton() {}

    @Override
    public void insertMoney(Coin coin) {
        System.out.println("Coin accepted: " + coin.name() + "\n");
        this.vendingMachine.addCoin(coin);
    }

    @Override
    public void pressItemSelectButton() {
        this.vendingMachine.setVendingMachineState(new ItemSelectionVendingMachineState(this.vendingMachine));
    }

    @Override
    public void selectItem(int codeNumber) throws Exception {
        throw new Exception("Cannot select item in MONEY ACCEPTANCE state");
    }

    @Override
    public Item dispenseItem(int codeNumber) throws Exception {
        throw new Exception("Cannot dispense item in MONEY ACCEPTANCE state");
    }

    @Override
    public void refundExtraMoney(int extraAmountPaid) throws Exception {
        throw new Exception("Cannot refund in MONEY ACCEPTANCE state");
    }

    @Override
    public List<Coin> cancelTransactionAndRefund() {
        System.out.println("Please collect the refund amount from coin dispense tray.\n");
        this.vendingMachine.setVendingMachineState(new IdleVendingMachineState(this.vendingMachine));
        this.vendingMachine.setCoins(new ArrayList<>());
        return this.vendingMachine.getCoins();
    }
}
