package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWarehouseRepository extends JpaRepository<Warehouse,Integer> {
}
