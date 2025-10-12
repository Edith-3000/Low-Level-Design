package io.github.kapilchoudhary.atm.model;

import io.github.kapilchoudhary.atm.state.ATMState;
import lombok.Getter;
import lombok.Setter;

public class ATM {

    @Getter
    @Setter
    private ATMState currentState;

    public ATM() {}
}
