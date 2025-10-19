package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.service.CardService;
import io.github.kapilchoudhary.atm.service.CashInventoryService;
import io.github.kapilchoudhary.atm.service.model.DispenseCashResult;
import lombok.NonNull;

public class IdleState implements ATMState {

    @Override
    public void insertCard(@NonNull final ATM atm, @NonNull final Card card) {
        atm.setCurrentState(new CardInsertedState());
        atm.setInsertedCard(card);
        System.out.println("Card inserted: " + card.getCardNumber());
    }

    @Override
    public void ejectCard(@NonNull final ATM atm) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
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
    public DispenseCashResult withdraw(@NonNull final ATM atm, double amount, @NonNull final CashInventoryService cashInventoryService, @NonNull final CardService cardService) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }
}
