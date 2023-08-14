package com.simple_man_store.order.repository;

import com.simple_man_store.employee.model.Employee;
import com.simple_man_store.order.dto.TotalAccountDto;
import com.simple_man_store.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Order,Integer> {
    @Query(value = "select * from order_info where flag=1 and name like :searchName",nativeQuery = true)
    Page<Order> findOrderByNameContaining(Pageable pageable, @Param(value = "searchName") String searchName);
    @Query(value = "select * from order_info where flag=1 and account_id= :account_id",nativeQuery = true)
    Page<Order> findOrdersByAccount_Id(Pageable pageable, @Param(value = "account_id") int id);
    @Query(value = "update order_info set flag = 0 where id = (select max(id) from order_info)",nativeQuery = true)
    void deleteLast();
    @Query(value = "select oi.account_id as accountId, sum(od.price * od.quantity) as total from order_info oi join order_detail od on od.order_id = oi.id group by oi.account_id",nativeQuery = true)
    List<TotalAccountDto> getTotalAndAccount();
}
