package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISizeRepository extends JpaRepository<Size,Integer> {
}
