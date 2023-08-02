package com.simple_man_store.customer.repository;

import com.simple_man_store.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value = "select * from customer where flag = true and name like :name",nativeQuery = true)
    Page<Customer>findCustomerByNameContaining(Pageable pageable,@Param(value = "name") String name);
}
