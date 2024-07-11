package by.ycovich.catalog.repository;

import by.ycovich.catalog.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select * from catalog.t_product where c_title ilike :title", nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("title") String title);
}
