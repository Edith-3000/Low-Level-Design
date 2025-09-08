package io.github.kapilchoudhary.snakeandfoodgame.model.food;

import io.github.kapilchoudhary.snakeandfoodgame.enums.FoodType;
import lombok.Getter;
import lombok.NonNull;

public abstract class Food {
    @Getter private final FoodType foodType;
    @Getter private final int score;

    public Food(@NonNull final FoodType foodType, final int score) {
        this.foodType = foodType;
        this.score = score;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (type=" + foodType + ", score=" + score + ")";
    }
}
