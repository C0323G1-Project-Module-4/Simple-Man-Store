package com.simple_man_store.product.service;

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
    Page<Product> findAll(Pageable pageable, String name);
    Page<Product> findAllPrice(Pageable pageable,double minPrice, double maxPrice);
}
