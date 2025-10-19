package io.github.kapilchoudhary.atm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class WithdrawResponse {
    private String message;
    private double remainingBalance;
    private Map<String, Integer> dispensedCash;
}
