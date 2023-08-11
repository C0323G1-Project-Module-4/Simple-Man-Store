package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findProductByNameContainingAndPriceBetweenAndCategoryNameContaining(Pageable pageable, String name,
                                                                          Double minPrice, Double maxPrice,
                                                                            String categoryName);
    @Query(value = "SELECT * FROM product JOIN category on product.category_id = category.id " +
            "JOIN warehouse on product.id = warehouse.product_id " +
            "JOIN size on warehouse.size_id = size.id WHERE category.name in :category_name and size.name in :size_name " +
            "ORDER BY product.price ASC ",nativeQuery = true)
    Page<Product> findListProductASC(Pageable pageable, @Param("category_name") List<String> categoryName, @Param("size_name") List<String> sizeName);
    @Query(value = "SELECT * FROM product JOIN category on product.category_id = category.id " +
            "JOIN warehouse on product.id = warehouse.product_id JOIN size on warehouse.size_id = size.id " +
            "WHERE category.name in :category_name and size.name in :size_name ORDER BY product.price DESC ",nativeQuery = true)
    Page<Product> findListProductDESC(Pageable pageable, @Param("category_name") List<String> categoryName,@Param("size_name") List<String> sizeName);
}
