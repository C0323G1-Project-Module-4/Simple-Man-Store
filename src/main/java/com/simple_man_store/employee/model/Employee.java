package com.simple_man_store.employee.model;

import com.simple_man_store.account.model.Account;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String dob;
    private String phone_number;
    private String email;
    private boolean gender;
    private String address;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
