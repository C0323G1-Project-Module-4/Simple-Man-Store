package com.simple_man_store.employee.controller;

import com.simple_man_store.employee.model.Employee;
import com.simple_man_store.employee.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/employee")
public class RestEmployeeController {
    @Autowired
    IEmployeeService employeeService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Employee> showEmployeeDetail(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        System.out.println(employee);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
