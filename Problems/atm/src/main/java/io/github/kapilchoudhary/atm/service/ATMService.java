package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import io.github.kapilchoudhary.atm.exception.CardInactiveException;
import io.github.kapilchoudhary.atm.exception.CardNotFoundException;
import io.github.kapilchoudhary.atm.exception.InvalidPinException;
import io.github.kapilchoudhary.atm.exception.NoCardException;
import io.github.kapilchoudhary.atm.model.ATM;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ATMService {

    private final ATM atm;
    private final CardRepository cardRepository;

    public void insertCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                        .orElseThrow(() -> new CardNotFoundException(cardNumber));

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
}
