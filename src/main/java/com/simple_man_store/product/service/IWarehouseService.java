package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Warehouse;

import java.util.List;

public interface IWarehouseService {
    List<Warehouse> showListWarehouse();
    void addWareHouse(Warehouse warehouse);
    void deleteWareHouse(Warehouse warehouse);
    Warehouse selectWarehouseByProductId(int id);
    void editWareHouse(Warehouse warehouse);
}
