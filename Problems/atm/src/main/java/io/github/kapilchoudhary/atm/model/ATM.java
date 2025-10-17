package io.github.kapilchoudhary.atm.model;

import io.github.kapilchoudhary.atm.enums.TransactionType;
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

    public void checkBalance() {
        this.currentState.checkBalance(this);
    }

    public void withdrawal(double amount) {
        this.currentState.withdrawal(this, amount);
    }
}
