package io.github.kapilchoudhary.atm.state;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.service.CardService;
import io.github.kapilchoudhary.atm.service.CashInventoryService;
import io.github.kapilchoudhary.atm.service.model.DispenseCashResult;
import lombok.NonNull;

public interface ATMState {

    void insertCard(@NonNull final ATM atm, @NonNull final Card card);
    void ejectCard(@NonNull final ATM atm);
    void enterPin(@NonNull final ATM atm);

    void selectTransaction(@NonNull final ATM atm, @NonNull final TransactionType transactionType);
    double checkBalance(@NonNull final ATM atm);
    DispenseCashResult withdraw(@NonNull final ATM atm, final double amount, @NonNull final CashInventoryService cashInventoryService, @NonNull final CardService cardService);

    // --- For future extensibility ---
    default void depositCash(@NonNull final ATM atm, double amount) {
        throw new UnsupportedOperationException("Deposit not supported yet.");
    }

    default void transferFunds(@NonNull final ATM atm, @NonNull final String toAccountNumber, double amount) {
        throw new UnsupportedOperationException("Transfer funds not supported yet.");
    }
}
