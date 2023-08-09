package com.simple_man_store.order.service;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;


    @Override
    public void add(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllInPage(Pageable pageable, String searchName) {
        searchName = "%" + searchName + "%";
        return orderRepository.findOrderByNameContaining(pageable,searchName);
    }

    @Override
    public Order getById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null){
            return false;
        }
        order.setFlag(false);
        orderRepository.save(order);
        return true;
    }

    @Override
    public Page<Order> getByAcount(Pageable pageable, Account account) {
        return orderRepository.findOrdersByAccount_Id(pageable,account.getId());
    }
}
