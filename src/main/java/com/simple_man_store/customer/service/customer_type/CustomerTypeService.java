package com.simple_man_store.customer.service.customer_type;

import com.simple_man_store.customer.model.CustomerType;
import com.simple_man_store.customer.repository.ICustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTypeService implements ICustomerTypeService {
    @Autowired
    private ICustomerTypeRepository customerTypeRepository;

    @Override
    public boolean add(CustomerType customerType) {
        CustomerType check = customerTypeRepository.save(customerType);
        if (check == null) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void delete(Integer id) {
        customerTypeRepository.deleteById(id);
    }

    @Override
    public List<CustomerType> findAll() {
        return customerTypeRepository.findAll();
    }
}
