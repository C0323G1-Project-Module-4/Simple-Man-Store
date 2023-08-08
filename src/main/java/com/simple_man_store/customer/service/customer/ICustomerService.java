package com.simple_man_store.customer.service.customer;

import com.simple_man_store.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    boolean add(Customer customer);
    boolean delete(Integer id);
    boolean edit(Customer customer);
    Page<Customer> findAllPage(Pageable pageable,String name);
    Page<Customer> findAllPageCustomerTypeId(Pageable pageable, String name, String[] customerTypeId);
    Page<Customer> findAllPageCustomerTypeIdGender(Pageable pageable, String name, String[] customerTypeId,Boolean gender);
    Customer findById(Integer id);
}
