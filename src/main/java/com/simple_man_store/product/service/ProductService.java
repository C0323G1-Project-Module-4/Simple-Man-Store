package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product selectProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void editProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProduct(Pageable pageable, String name, Double minPrice, Double maxPrice, String categoryName) {
        return productRepository.findProductByNameContainingAndPriceBetweenAndCategoryNameContaining(pageable, name, minPrice, maxPrice, categoryName);
    }

}
