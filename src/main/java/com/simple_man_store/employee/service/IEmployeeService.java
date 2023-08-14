package com.simple_man_store.employee.service;

import com.simple_man_store.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<Employee> findAll(Pageable pageable, String searchName, String phoneNumber);

    Employee findById(int id);

    void editEmployee(Employee employee);

    boolean remove(int id);
}
