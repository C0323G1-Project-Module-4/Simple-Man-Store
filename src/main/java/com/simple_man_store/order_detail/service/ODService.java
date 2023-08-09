package com.simple_man_store.order_detail.service;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.order_detail.model.OrderDetail;
import com.simple_man_store.order_detail.repository.IODRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ODService implements IODService{
    @Autowired
    private IODRepository odRepository;

    @Override
    public void addAll(Set<OrderDetail> orderDetail) {
        odRepository.saveAll(orderDetail);
    }

    @Override
    public List<OrderDetail> getByOrder(Order order) {
        return odRepository.findOrderDetailsByOrder(order);
    }
}
