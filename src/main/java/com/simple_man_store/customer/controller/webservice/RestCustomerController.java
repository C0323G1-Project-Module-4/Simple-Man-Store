package com.simple_man_store.customer.controller.webservice;

import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/customers")
public class RestCustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Customer> detailCustomer(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        System.out.println(customer);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
