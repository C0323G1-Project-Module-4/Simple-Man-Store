package com.simple_man_store.order_detail.repository;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.order_detail.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IODRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findOrderDetailsByOrder(Order order);
}
