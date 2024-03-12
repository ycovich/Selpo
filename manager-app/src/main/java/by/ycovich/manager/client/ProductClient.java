package by.ycovich.manager.client;

import by.ycovich.manager.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductClient {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(int productId, String title, String details);

    void deleteProduct(int productId);
}
