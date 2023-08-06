package com.simple_man_store.product.service;

import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.repository.ISizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService implements ISizeService{
    @Autowired
    private ISizeRepository sizeRepository;
    @Override
    public Size selectSizeById(int id) {
        return sizeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Size> showListSize() {
        return sizeRepository.findAll();
    }
}
