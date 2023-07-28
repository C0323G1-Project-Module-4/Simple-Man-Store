package com.simple_man_store.order.model;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.order_detail.model.OrderDetail;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_info")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false)
    private String address;
    @Column(columnDefinition = "date")
    private String order_date;
    @Column(columnDefinition = "date")
    private String payment_date;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @ManyToOne
    @JoinColumn(name = "account_id", unique = true, nullable = false, referencedColumnName = "id")
    private Account account;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetailSet;

}
