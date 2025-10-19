package io.github.kapilchoudhary.atm.service;

import io.github.kapilchoudhary.atm.model.CashInventory;
import io.github.kapilchoudhary.atm.repository.CashInventoryRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void decreaseCountForDenomination(int denomination, int count) {
        CashInventory cashInventory = cashRepo.findByDenomination(denomination)
                .orElseThrow(() -> new RuntimeException("Denomination not found: ₹ " + denomination));

        if (cashInventory.getCount() < count) {
            throw new RuntimeException("Not enough notes of denomination: ₹ " + denomination);
        }

        cashInventory.setCount(cashInventory.getCount() - count);
        cashRepo.save(cashInventory);
    }
}
