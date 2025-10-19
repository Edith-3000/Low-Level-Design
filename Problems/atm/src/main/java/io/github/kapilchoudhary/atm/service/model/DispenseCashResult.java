package io.github.kapilchoudhary.atm.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DispenseCashResult {

    private Map<Integer, Integer> dispensedMap;
    private double remainingCardBalance;
}
