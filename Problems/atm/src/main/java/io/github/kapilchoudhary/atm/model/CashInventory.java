package io.github.kapilchoudhary.atm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cash_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // denomination of the note, e.g., 2000, 500, 200, 100
    @Column(nullable = false)
    private int denomination;

    // count of notes of this denomination available in ATM
    @Column(nullable = false)
    private int count;

    /**
     * Calculates total amount for this denomination.
     * Example: denomination=500, count=10 => total=5000
     */
    public int getTotalAmount() {
        return denomination * count;
    }

    @Override
    public String toString() {
        return "₹" + denomination + " x " + count + " = " + getTotalAmount();
    }
}
