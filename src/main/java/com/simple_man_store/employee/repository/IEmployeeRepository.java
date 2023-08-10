package com.simple_man_store.employee.repository;

import com.simple_man_store.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query(value = "select * from employee where flag=true and name like :searchName and phone_number like :phoneNumber",nativeQuery = true)
    Page<Employee> findEmployeeByNameAndPhoneNumberContaining( Pageable pageable,@Param(value = "searchName") String searchName,@Param(value = "phoneNumber") String phoneNumber);
}
