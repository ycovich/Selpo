package by.ycovich.catalog.repository;

import by.ycovich.catalog.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private List<Product> products = Collections.synchronizedList(new LinkedList<>());

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(products.stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId)
                .orElse(0) + 1);
        products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return products.stream()
                .filter(product -> Objects.equals(productId, product.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        products.removeIf(product -> Objects.equals(id, product.getId()));
    }
}
