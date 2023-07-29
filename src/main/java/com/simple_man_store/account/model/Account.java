package com.simple_man_store.account.model;

import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.employee.model.Employee;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id",nullable = false,columnDefinition = "int default 1",unique = true)
    private Role role;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, optional = false)
    private Customer customer;
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY,optional = false)
    private Employee employee;


}
