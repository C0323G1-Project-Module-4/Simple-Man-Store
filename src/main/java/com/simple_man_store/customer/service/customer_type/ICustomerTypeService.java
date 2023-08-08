package com.simple_man_store.customer.service.customer_type;

import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.model.CustomerType;

import java.util.List;

public interface ICustomerTypeService {
    boolean add(CustomerType customerType);
    void delete(Integer id);

    List<CustomerType> findAll();
    CustomerType findByName(Integer id);
}
