package io.github.kapilchoudhary.atm.controller;

import io.github.kapilchoudhary.atm.model.Card;
import io.github.kapilchoudhary.atm.service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atm")
@RequiredArgsConstructor
public class ATMController {

    private final ATMService atmService;

    @PostMapping("/insert-card")
    public ResponseEntity<String> insertCard(@RequestBody Card card) {
        atmService.insertCard(card);
    }
}
