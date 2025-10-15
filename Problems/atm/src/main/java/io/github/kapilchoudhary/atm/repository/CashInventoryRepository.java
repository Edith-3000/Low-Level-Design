package io.github.kapilchoudhary.atm.repository;

import io.github.kapilchoudhary.atm.model.CashInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashInventoryRepository extends JpaRepository<CashInventory, Long> {
}
