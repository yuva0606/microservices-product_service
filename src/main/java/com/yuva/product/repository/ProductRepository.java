package com.yuva.product.repository;

import com.yuva.product.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findAll();
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double min, Double max);
    List<Product> findByCategoryAndPriceGreaterThanEqual(String category, Double min);
    List<Product> findByCategoryAndPriceBetween(String category, Double min, Double max);
    List<Product> findByCategoryAndPriceLessThanEqual(String category, Double max);

}
