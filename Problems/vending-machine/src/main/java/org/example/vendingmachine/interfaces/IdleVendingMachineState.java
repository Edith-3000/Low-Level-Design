package org.example.vendingmachine.interfaces;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;
import org.example.vendingmachine.services.VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class IdleVendingMachineState implements VendingMachineState {
    private final VendingMachine vendingMachine;

    public IdleVendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        System.out.println("Vending machine's state: IDLE.\n");
    }

    @Override
    public void pressInsertMoneyButton() {
        this.vendingMachine.setVendingMachineState(new MoneyAcceptanceVendingMachineState(this.vendingMachine));
    }

    @Override
    public void insertMoney(Coin coin) throws Exception {
        throw new Exception("Cannot insert money in IDLE state");
    }

    @Override
    public void pressItemSelectButton() throws Exception {
        throw new Exception("Cannot press item select button in IDLE state");
    }

    @Override
    public void selectItem(int codeNumber) throws Exception {
        throw new Exception("Cannot select item in IDLE state");
    }

    @Override
    public Item dispenseItem(int codeNumber) throws Exception {
        throw new Exception("Cannot dispense item in IDLE state");
    }

    @Override
    public void refundExtraMoney(int extraAmountPaid) throws Exception {
        throw new Exception("Cannot refund in IDLE state");
    }

    @Override
    public List<Coin> cancelTransactionAndRefund() {
        return new ArrayList<>();
    }
}
