package org.example.onlineshopping.interfaces.search;

import lombok.NonNull;
import org.example.onlineshopping.models.Product;

import java.util.List;

public class CategorySearchStrategy implements ISearchStrategy {
    @Override
    public List<Product> search(@NonNull final String query, @NonNull final List<Product> products) {
        return products.stream()
                .filter(p -> p.getCategory().getName().equalsIgnoreCase(query))
                .toList();
    }
}
