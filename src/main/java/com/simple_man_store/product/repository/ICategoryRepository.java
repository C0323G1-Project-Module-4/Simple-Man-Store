package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Integer> {
}
