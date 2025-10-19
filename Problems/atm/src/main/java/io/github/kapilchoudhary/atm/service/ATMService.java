package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.dto.WithdrawResponse;
import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.exception.*;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.service.model.DispenseCashResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ATMService {

    private final ATM atm;
    private final CardService cardService;
    private final CashInventoryService cashInventoryService;

    public void insertCard(String cardNumber) {
        Card card = cardService.getCard(cardNumber);

        if (!card.isActive()) {
            throw new CardInactiveException(cardNumber);
        }

        atm.insertCard(card);
    }

    public void enterPin(String pin) {
        Card card = atm.getInsertedCard();

        if (card == null) {
            throw new NoCardException();
        }

        if (!pin.equals(card.getPin())) {
            throw new InvalidPinException(pin);
        }

        atm.enterPin(pin);
    }

    public void ejectCard() {
        atm.ejectCard();
    }

    public void selectTransaction(TransactionType transactionType) {
        atm.selectTransaction(transactionType);
    }

    public double checkBalance() {
        return atm.checkBalance();
    }

    /**
     * To ensure that both the operation -
     * 1. ATM cash inventory update.
     * 2. Card balance deduction.
     * are either successful or failed (either both committed or both rolled back) we put them in a single transaction.
     */
    @Transactional
    public WithdrawResponse withdraw(double amount) {
        if ((amount <= 0) || (amount % 100 != 0)) {
            throw new InvalidWithdrawAmountException(amount);
        }

        DispenseCashResult dispenseCashResult = atm.withdraw(amount, cashInventoryService, cardService);

        Map<String, Integer> withdrawResponseMap = new LinkedHashMap<>();

        for (Map.Entry<Integer, Integer> entry: dispenseCashResult.getDispensedMap().entrySet()) {
            String mapKey = "₹ " + entry.getKey();
            withdrawResponseMap.put(mapKey, entry.getValue());
        }

        return WithdrawResponse.builder()
                .message("Thank you! Please collect your cash and do not forget to remove the card.")
                .remainingBalance(dispenseCashResult.getRemainingCardBalance())
                .dispensedCash(withdrawResponseMap)
                .build();
    }
}
