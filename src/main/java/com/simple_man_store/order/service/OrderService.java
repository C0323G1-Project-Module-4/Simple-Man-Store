package com.simple_man_store.order.service;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.repository.IAccountRepository;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.customer.service.customer_type.ICustomerTypeService;
import com.simple_man_store.order.dto.TotalAccountDto;
import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.lang.Double.parseDouble;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICustomerTypeService customerTypeService;
    @Override
    public void add(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllInPage(Pageable pageable, String searchName) {
        searchName = "%" + searchName + "%";
        return orderRepository.findOrderByNameContaining(pageable,searchName);
    }

    @Override
    public Order getById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null){
            return false;
        }
        order.setFlag(false);
        orderRepository.save(order);
        return true;
    }

    @Override
    public Page<Order> getByAcount(Pageable pageable, Account account) {
        return orderRepository.findOrdersByAccount_Id(pageable,account.getId());
    }

    @Override
    public void deleteLast() {
        orderRepository.deleteLast();
    }

    @Override
    public void checkTotalToRankUpCustomer() {
      List<TotalAccountDto> totalAccountDtoList =  orderRepository.getTotalAndAccount();
      for (TotalAccountDto totalAccountDto : totalAccountDtoList){
          Account account = accountRepository.findById(Integer.parseInt(totalAccountDto.getAccountId())).get();
          Customer customer = customerService.findByEmail(account.getEmail());
          if(Double.parseDouble(totalAccountDto.getTotal())>10000000){
              customer.setCustomerType(customerTypeService.findByName(2));
              customerService.edit(customer);
          }
          if(Double.parseDouble(totalAccountDto.getTotal())>20000000){
              customer.setCustomerType(customerTypeService.findByName(3));
              customerService.edit(customer);
          }
      }
    }
}
