package io.github.kapilchoudhary.atm.controller;

import io.github.kapilchoudhary.atm.dto.EnterPinRequest;
import io.github.kapilchoudhary.atm.dto.InsertCardRequest;
import io.github.kapilchoudhary.atm.dto.SelectTransactionRequest;
import io.github.kapilchoudhary.atm.service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
@RequiredArgsConstructor
public class ATMController {

    private final ATMService atmService;

    @PostMapping("/insert-card")
    public ResponseEntity<String> insertCard(@RequestBody InsertCardRequest request) {
        atmService.insertCard(request.getCardNumber());
        return ResponseEntity.ok("Card inserted successfully. ATM ready for PIN entry.");
    }

    @PostMapping("/enter-pin")
    public ResponseEntity<String> enterPin(@RequestBody EnterPinRequest request) {
        atmService.enterPin(request.getPin());
        return ResponseEntity.ok("Card authenticated. ATM ready for selecting a transaction option.");
    }

    @DeleteMapping("/eject-card")
    public ResponseEntity<String> ejectCard() {
        atmService.ejectCard();
        return ResponseEntity.ok("Card ejected successfully. Please collect your card.");
    }

    @PostMapping("/select-transaction")
    public ResponseEntity<String> selectTransaction(@RequestBody SelectTransactionRequest request) {
        atmService.selectTransaction(request.getTransactionType());
        return ResponseEntity.ok("Transaction selected: " + request.getTransactionType());
    }
}
