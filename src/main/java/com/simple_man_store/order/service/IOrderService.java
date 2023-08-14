package com.simple_man_store.order.service;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    void add(Order order);
    Page<Order> findAllInPage(Pageable pageable, String searchName);

    Order getById(int id);

    boolean delete(int id);

    Page<Order> getByAcount(Pageable pageable, Account account);

    void deleteLast();

    void checkTotalToRankUpCustomer();

}
