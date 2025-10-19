package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.exception.CardNotFoundException;
import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));

        return card;
    }

    @Transactional
    public void updateCardBalance(Card card, double amount) {
        double currentBalance = card.getBalance();
        double updatedBalance = currentBalance - amount;

        if (updatedBalance < 0) {
            throw new RuntimeException("Card balance cannot be less than 0.");
        }

        card.setBalance(updatedBalance);
        cardRepository.save(card);
    }
}
