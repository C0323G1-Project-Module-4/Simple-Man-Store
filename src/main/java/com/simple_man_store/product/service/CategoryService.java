package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> showListCategory() {
        return categoryRepository.findAll();
    }
}
