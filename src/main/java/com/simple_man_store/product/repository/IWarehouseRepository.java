package com.simple_man_store.product.repository;

import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWarehouseRepository extends JpaRepository<Warehouse,Integer> {
    Warehouse findWarehouseByProductId(int id);
}
