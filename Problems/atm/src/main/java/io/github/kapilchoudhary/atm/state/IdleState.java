package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import lombok.NonNull;

public class IdleState implements ATMState {

    @Override
    public void insertCard(@NonNull final ATM atm, @NonNull final Card card) {
        atm.setCurrentState(new CardInsertedState());
    }

    @Override
    public void ejectCard(@NonNull final ATM atm) {
        System.out.println("No card to eject — ATM is idle.");
    }

    @Override
    public void enterPin(@NonNull final ATM atm, @NonNull final String pin) {
        System.out.println("Please insert card first.");
    }

    @Override
    public void selectTransaction(@NonNull final ATM atm, @NonNull final TransactionType transactionType) {
        System.out.println("No card inserted. Insert card first.");
    }

    @Override
    public void checkBalance(@NonNull final ATM atm) {
        System.out.println("Please insert card first.");
    }

    @Override
    public void withdrawal(@NonNull final ATM atm, double amount) {
        System.out.println("Please insert card first.");
    }
}
