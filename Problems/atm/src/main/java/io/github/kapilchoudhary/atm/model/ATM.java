package io.github.kapilchoudhary.atm.model;

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

    public ATM() {
        this.currentState = new IdleState();
    }

    public void insertCard(Card card) {
        currentState.insertCard(this, card);
    }

    public void ejectCard() {
        currentState.ejectCard(this);
    }


}
