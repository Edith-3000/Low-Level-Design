package io.github.kapilchoudhary.snakeandfoodgame.model.food;

import io.github.kapilchoudhary.snakeandfoodgame.enums.FoodType;

public class Mushroom extends Food {
    public Mushroom() {
        super(FoodType.POISONOUS, 0);
    }
}
