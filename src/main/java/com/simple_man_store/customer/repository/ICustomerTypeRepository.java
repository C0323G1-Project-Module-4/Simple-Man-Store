package com.simple_man_store.customer.repository;

import com.simple_man_store.customer.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerTypeRepository extends JpaRepository<CustomerType,Integer> {

}
