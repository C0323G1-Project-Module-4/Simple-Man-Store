package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Size;

import java.util.List;

public interface ISizeService {
    Size selectSizeById(int id);
    List<Size> showListSize();
}
