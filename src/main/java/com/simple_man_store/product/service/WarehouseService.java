package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Product;
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
    public List<Warehouse> showListWarehouse() {
        return warehouseRepository.findAll();
    }

    @Override
    public void addWareHouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteWareHouse(Warehouse warehouse) {
        warehouseRepository.delete(warehouse);
    }

    @Override
    public Warehouse selectWarehouseByProductId(int id) {
        return warehouseRepository.findWarehouseByProductId(id);
    }

    @Override
    public void editWareHouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }
}
