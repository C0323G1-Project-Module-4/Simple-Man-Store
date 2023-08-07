package com.simple_man_store.employee.service;

import com.simple_man_store.employee.model.Employee;
import com.simple_man_store.employee.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Override
    public Page<Employee> findAll(Pageable pageable, String name) {
        return employeeRepository.findEmployeeByNameContaining(pageable,"%"+name+"%");
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public void editEmployee(Employee employee) {

        employeeRepository.save(employee);
    }

    @Override
    public boolean remove(int id) {
        Employee employee = employeeRepository.findById(id).get();
        if (employee==null) {
            return false;
        }
        employee.setFlag(false);
        employeeRepository.save(employee);
        return true;
    }
}
