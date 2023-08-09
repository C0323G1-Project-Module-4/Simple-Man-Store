package com.simple_man_store.customer.service.customer;

import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.model.CustomerType;
import com.simple_man_store.customer.repository.ICustomerRepository;
import com.simple_man_store.customer.until.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;


    @Override
    public boolean add(Customer customer) {
        customer.setFlag(true);
        CustomerType customerType = new CustomerType();
        customerType.setId(1);
        customer.setCustomerType(customerType);
        Customer check = customerRepository.save(customer);
        if (check == null) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean delete(Integer id) {
        Customer customer = customerRepository.findById(id).get();
        if (customer == null){
            return false;
        }else {
            customer.setFlag(false);
            customerRepository.save(customer);
            return true;
        }
    }

    @Override
    public boolean edit(Customer customer) {
        Customer check = customerRepository.save(customer);
        if (check == null) {
            return false;
        } else {
            return false;
        }

    }

    @Override
    public Page<Customer> findAllPage(Pageable pageable, String name) {
        Page<Customer> customerPage = customerRepository.findCustomerByNameContaining(pageable, "%"+name+"%");
        for (Customer c:customerPage) {
            c.setDob(DateUtils.reverseDate(c.getDob()));
        }
        return customerPage;
    }

    @Override
    public Page<Customer> findAllPageCustomerTypeId(Pageable pageable, String name, String[] customerTypeId) {
        Page<Customer> customerPage;
        List<Integer> customerTypeIds = new ArrayList<>();
        for (String s:customerTypeId ) {
            customerTypeIds.add(Integer.valueOf(s));
        }
        customerPage = customerRepository.findCustomerByNameContainingCAndCustomerType_Id(pageable,"%"+name+"%",customerTypeIds);
        for (Customer c:customerPage) {
            c.setDob(DateUtils.reverseDate(c.getDob()));
        }
        return customerPage;

    }

    @Override
    public Customer findById(Integer id) {
        Customer customerFind = customerRepository.findById(id).get();
        customerFind.setDob(DateUtils.reverseDate(customerFind.getDob()));
        return customerFind;
    }

    @Override
    public Customer searchById(Integer id) {
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    @Override
    public Page<Customer> findAllPageCustomerTypeIdGender(Pageable pageable, String name, String[] customerTypeId, Boolean gender) {
        List<Integer> customerTypeIds = new ArrayList<>();
        for (String s:customerTypeId ) {
            customerTypeIds.add(Integer.valueOf(s));
        }
        return customerRepository.findCustomerByNameContainingCAndCustomerType_IdAndGender(pageable,name,customerTypeIds,gender);
    }
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
