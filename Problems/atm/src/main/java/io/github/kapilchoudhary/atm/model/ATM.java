package io.github.kapilchoudhary.atm.model;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.service.CardService;
import io.github.kapilchoudhary.atm.service.CashInventoryService;
import io.github.kapilchoudhary.atm.service.model.DispenseCashResult;
import io.github.kapilchoudhary.atm.state.ATMState;
import io.github.kapilchoudhary.atm.state.IdleState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ATM {

    private ATMState currentState;

    private Card insertedCard;

    public ATM() {
        this.currentState = new IdleState();
    }

    public void insertCard(Card card) {
        currentState.insertCard(this, card);
    }

    public void ejectCard() {
        currentState.ejectCard(this);
    }

    public void enterPin(String pin) {
        currentState.enterPin(this);
    }

    public void selectTransaction(TransactionType transactionType) {
        this.currentState.selectTransaction(this, transactionType);
    }

    public double checkBalance() {
        return this.currentState.checkBalance(this);
    }

    public DispenseCashResult withdraw(double amount, CashInventoryService cashInventoryService, CardService cardService) {
        return this.currentState.withdraw(this, amount, cashInventoryService, cardService);
    }
}
