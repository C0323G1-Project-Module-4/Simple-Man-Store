package com.simple_man_store.customer.service.customer;

import com.simple_man_store.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    boolean add(Customer customer);
    boolean delete(Integer id);
    boolean edit(Customer customer);
    Page<Customer> findAllPage(Pageable pageable,String name);
    Customer findById(Integer id);
<<<<<<< HEAD
=======

    Customer findByEmail(String email);

    void save(Customer customer);
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
}
