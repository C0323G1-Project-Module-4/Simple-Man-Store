package com.simple_man_store.customer.repository;

import com.simple_man_store.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "select * from customer where flag = true and name like :name ", nativeQuery = true)
    Page<Customer> findCustomerByNameContaining(Pageable pageable, @Param(value = "name") String name);

    @Query(value = "select * from customer where flag = true and name like :name and customer_type_id in :customer_type_id", nativeQuery = true)
    Page<Customer> findCustomerByNameContainingCAndCustomerType_Id(Pageable pageable, @Param(value = "name") String name, @Param(value = "customer_type_id") List<Integer> customerTypeIds);

    @Query(value = "select * from customer where flag = true and name like :name and customer_type_id in :customer_type_id and gender = :gender", nativeQuery = true)
    Page<Customer> findCustomerByNameContainingCAndCustomerType_IdAndGender(Pageable pageable, @Param(value = "name") String name, @Param(value = "customer_type_id") List<Integer> customerTypeIds,@Param(value = "gender") Boolean gender);

}
