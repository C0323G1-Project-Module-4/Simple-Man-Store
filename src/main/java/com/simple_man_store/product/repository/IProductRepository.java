package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findProductByNameContainingAndPriceBetweenAndCategoryNameContaining(Pageable pageable, String name,
                                                                          Double minPrice, Double maxPrice,
                                                                            String categoryName);
}
