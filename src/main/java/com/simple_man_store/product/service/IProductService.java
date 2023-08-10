package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    void addProduct(Product product);
    Product selectProductById(int id);
    void deleteProduct(Product product);
    void editProduct(Product product);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findProduct(Pageable pageable, String name, Double minPrice, Double maxPrice, String categoryName);
    Page<Product> findProductSearch(Pageable pageable,String [] categoryName,String [] sizeName,String order);

}
