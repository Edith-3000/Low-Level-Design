package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import lombok.NonNull;

public class CardInsertedState implements ATMState {

    @Override
    public void insertCard(@NonNull final ATM atm, @NonNull final Card card) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void ejectCard(@NonNull final ATM atm) {
        atm.setCurrentState(new IdleState());
    }

    @Override
    public void enterPin(@NonNull final ATM atm, @NonNull final String pin) {
        atm.setCurrentState(new AuthenticatedState());
    }

    @Override
    public void selectTransaction(@NonNull final ATM atm, @NonNull final TransactionType transactionType) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void checkBalance(@NonNull final ATM atm) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void withdrawal(@NonNull final ATM atm, double amount) {
        throw new UnsupportedOperationException("Operation not allowed in current state: " + this.getClass().getSimpleName());
    }
}
