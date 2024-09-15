package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;

public class ProductCategory {
    @Getter
    private final String name;

    public ProductCategory(@NonNull final String name) {
        this.name = name;
    }
}
