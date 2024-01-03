package org.example.vendingmachine.interfaces;

import org.example.vendingmachine.models.Coin;
import org.example.vendingmachine.models.Item;

import java.util.List;

public interface VendingMachineState {
    public void pressInsertMoneyButton() throws Exception;
    public void insertMoney(Coin coin) throws Exception;
    public void pressItemSelectButton() throws Exception;
    public void selectItem(int codeNumber) throws Exception;
    public Item dispenseItem(int codeNumber) throws Exception;
    public void refundExtraMoney(int extraAmountPaid) throws Exception;
    public List<Coin> cancelTransactionAndRefund() throws Exception;
}
