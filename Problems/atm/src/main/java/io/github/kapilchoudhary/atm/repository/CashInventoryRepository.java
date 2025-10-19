package io.github.kapilchoudhary.atm.repository;

import io.github.kapilchoudhary.atm.model.CashInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashInventoryRepository extends JpaRepository<CashInventory, Long> {

    Optional<CashInventory> findByDenomination(int denomination);
}
