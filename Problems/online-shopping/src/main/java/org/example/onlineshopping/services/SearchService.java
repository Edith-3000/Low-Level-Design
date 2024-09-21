package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.interfaces.search.ISearchStrategy;
import org.example.onlineshopping.models.Product;

import java.util.List;

public class SearchService {
    private static volatile SearchService instance;
    private ISearchStrategy searchStrategy;

    private SearchService() {}

    public static SearchService getInstance() {
        if (instance == null) {
            synchronized (SearchService.class) {
                if (instance == null) {
                    instance = new SearchService();
                }
            }
        }

        return instance;
    }

    public void setSearchStrategy(@NonNull final ISearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Product> search(@NonNull final String query) {
        List<Product> products = InventoryService.getInstance().getProducts();
        return searchStrategy.search(query, products);
    }
}
