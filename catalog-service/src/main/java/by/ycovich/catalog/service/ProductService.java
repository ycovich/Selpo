package by.ycovich.catalog.service;

import by.ycovich.catalog.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Integer productId);

    void updateProduct(Integer id, String title, String details);

    void deleteById(Integer id);
}
