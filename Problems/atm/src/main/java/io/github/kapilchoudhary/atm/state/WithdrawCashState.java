package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.exception.InsufficientATMBalanceException;
import io.github.kapilchoudhary.atm.exception.InsufficientCardBalanceException;
import io.github.kapilchoudhary.atm.exception.InsufficientDenominationsException;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.model.CashInventory;
import io.github.kapilchoudhary.atm.service.CardService;
import io.github.kapilchoudhary.atm.service.CashInventoryService;
import io.github.kapilchoudhary.atm.service.model.DispenseCashResult;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WithdrawCashState implements ATMState {

    @Override
    public void insertCard(@NonNull final ATM atm, @NonNull final Card card) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void ejectCard(@NonNull final ATM atm) {
        atm.setInsertedCard(null);
        atm.setCurrentState(new IdleState());
    }

    @Override
    public void enterPin(@NonNull final ATM atm) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void selectTransaction(@NonNull final ATM atm, @NonNull final TransactionType transactionType) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public double checkBalance(@NonNull final ATM atm) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public DispenseCashResult withdraw(
            @NonNull final ATM atm,
            double amount,
            @NonNull final CashInventoryService cashInventoryService,
            @NonNull final CardService cardService
    ) {
        Card card = atm.getInsertedCard();

        if (card.getBalance() < amount) {
            throw new InsufficientCardBalanceException(card.getBalance());
        }

        int totalCashAvailable = cashInventoryService.getTotalCashAvailable();
        if (totalCashAvailable < amount) {
            throw new InsufficientATMBalanceException(totalCashAvailable);
        }

        List<CashInventory> denominations = cashInventoryService.getAllDenominations();
        Map<Integer, Integer> dispensedMap = dispenseCash(amount, denominations);

        if (dispensedMap.isEmpty()) {
            throw new InsufficientDenominationsException();
        }

        updateATMCashInventory(dispensedMap, cashInventoryService);
        cardService.updateCardBalance(atm.getInsertedCard(), amount);

        double remainingCardBalance = card.getBalance();

        atm.setInsertedCard(null);
        atm.setCurrentState(new IdleState());

        return new DispenseCashResult(dispensedMap, remainingCardBalance);
    }

    private Map<Integer, Integer> dispenseCash(double amount, List<CashInventory> denominations) {
        // Give higher denomination notes first
        denominations.sort((a, b) -> Integer.compare(b.getDenomination(), a.getDenomination()));

        Map<Integer, Integer> dispensedMap = new LinkedHashMap<>();

        for (CashInventory denomination: denominations) {
            if (amount == 0) {
                break;
            }

            int count = Math.min((int) amount / denomination.getDenomination(), denomination.getCount());

            if (count > 0) {
                dispensedMap.put(denomination.getDenomination(), count);
                amount -= (denomination.getDenomination() * count);
            }
        }

        if (amount != 0) {
            dispensedMap = new LinkedHashMap<>();
        }

        return dispensedMap;
    }

    private void updateATMCashInventory(Map<Integer, Integer> updateMap, CashInventoryService cashInventoryService) {
        updateMap.forEach((denomination, count) -> {
            cashInventoryService.decreaseCountForDenomination(denomination, count);
        });
    }
}
