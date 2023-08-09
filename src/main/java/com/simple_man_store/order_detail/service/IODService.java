package com.simple_man_store.order_detail.service;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.order_detail.model.OrderDetail;

import java.util.List;
import java.util.Set;

public interface IODService {
    void addAll(Set<OrderDetail> orderDetail);

    List<OrderDetail> getByOrder(Order order);
}
