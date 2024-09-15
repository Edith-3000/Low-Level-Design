package org.example.onlineshopping.interfaces.search;

import lombok.NonNull;
import org.example.onlineshopping.models.Product;

import java.util.List;

public interface ISearchStrategy {
    List<Product> search(@NonNull final String query, @NonNull final List<Product> products);
}
