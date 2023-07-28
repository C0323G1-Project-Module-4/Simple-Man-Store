package com.simple_man_store.customer.model;

import com.simple_man_store.account.model.Account;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "date")
    private String dob;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone_number;
    private boolean gender;
    private String address;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @ManyToOne
    @JoinColumn(name = "customer_type_id", referencedColumnName = "id",nullable = false)
    private CustomerType customerType;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
