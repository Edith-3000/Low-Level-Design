package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import lombok.NonNull;

public interface ATMState {

    void insertCard(@NonNull final ATM atm, @NonNull final Card card);
    void ejectCard(@NonNull final ATM atm);
    void enterPin(@NonNull final ATM atm, @NonNull final String pin);

    void selectTransaction(@NonNull final ATM atm, @NonNull final TransactionType transactionType);
    void checkBalance(@NonNull final ATM atm);
    void withdrawal(@NonNull final ATM atm, final double amount);

    // --- For future extensibility ---
    default void depositCash(@NonNull final ATM atm, double amount) {
        throw new UnsupportedOperationException("Deposit not supported yet.");
    }

    default void transferFunds(@NonNull final ATM atm, @NonNull final String toAccountNumber, double amount) {
        throw new UnsupportedOperationException("Transfer funds not supported yet.");
    }
}
