package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Warehouse;
import com.simple_man_store.product.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService implements IWarehouseService{
    @Autowired
    private IWarehouseRepository warehouseRepository;
    @Override
    public List<Warehouse> findALl() {
        return warehouseRepository.findAll();
    }
}
