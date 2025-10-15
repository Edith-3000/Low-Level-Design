package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import org.springframework.stereotype.Service;

@Service
public class ATMService {

    private final ATM atm;

    public void insertCard(Card card) {
        atm.insertCard(card);
    }
}
