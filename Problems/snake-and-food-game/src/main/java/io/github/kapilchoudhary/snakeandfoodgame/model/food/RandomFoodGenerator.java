package io.github.kapilchoudhary.snakeandfoodgame.model.food;

import java.util.Map;
import java.util.Random;

public class RandomFoodGenerator {
    private static final Random random = new Random();

    private static final Map<Class<? extends Food>, Integer> foodProbabilities = Map.of(
            Apple.class, 70,
            Kiwi.class, 20,
            Mushroom.class, 10
    );

    public static Food generateRandomFood() {
        int total = foodProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int rand = random.nextInt(total);

        int cumulative = 0;

        for (Map.Entry<Class<? extends Food>, Integer> entry: foodProbabilities.entrySet()) {
            cumulative += entry.getValue();

            if (rand < cumulative) {
                try {
                    return entry.getKey().getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create food: ", e);
                }
            }
        }

        throw new IllegalStateException("No food generated");
    }
}
