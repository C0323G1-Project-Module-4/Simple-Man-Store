package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.repository.ICategoryRepository;
import com.simple_man_store.product.repository.IProductRepository;
import com.simple_man_store.product.repository.ISizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ISizeRepository sizeRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

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

    @Override
    public Page<Product> findProductSearch(Pageable pageable, String[] categoryName, String[] sizeName, String order) {
        List<String> categoryNameList = new ArrayList<>();
        for (String n : categoryName) {
            categoryNameList.add(n);
        }
        List<String> sizeNameList = new ArrayList<>();
        for (String n : sizeName) {
            sizeNameList.add(n);
        }
        List<Size> sizeList = sizeRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryName.length == 0){
            for (Category n: categoryList ) {
                categoryNameList.add(n.getName());
            }
        }
        if (sizeName.length == 0){
            for (Size s:sizeList) {
                sizeNameList.add(s.getName());
            }
        }
        if (order.equals("ASC")) {
            return productRepository.findListProductASC(pageable, categoryNameList, sizeNameList);
        }
        return productRepository.findListProductDESC(pageable, categoryNameList, sizeNameList);
    }

    @Override
    public List<Product> bestSellers() {
        return productRepository.bestSellers();
    }

}
