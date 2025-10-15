package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.model.CashInventory;
import io.github.kapilchoudhary.atm.repository.CashInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashInventoryService {

    private final CashInventoryRepository cashRepo;

    public CashInventoryService(CashInventoryRepository cashRepo) {
        this.cashRepo = cashRepo;
    }

    public List<CashInventory> getAllDenominations() {
        return cashRepo.findAll();
    }

    public int getTotalCashAvailable() {
        return cashRepo.findAll().stream()
                .mapToInt(CashInventory::getTotalAmount)
                .sum();
    }
}
