package io.github.kapilchoudhary.atm.dto;

import io.github.kapilchoudhary.atm.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectTransactionRequest {
    private TransactionType transactionType;
}
