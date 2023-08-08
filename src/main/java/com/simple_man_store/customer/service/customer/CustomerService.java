package com.simple_man_store.customer.service.customer;

import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.model.CustomerType;
import com.simple_man_store.customer.repository.ICustomerRepository;
import com.simple_man_store.customer.service.customer_type.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return customerRepository.findCustomerByNameContaining(pageable, "%"+name+"%");
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id).get();
    }

<<<<<<< HEAD
=======
    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92

}
