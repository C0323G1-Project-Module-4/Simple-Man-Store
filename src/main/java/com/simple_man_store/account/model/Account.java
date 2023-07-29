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
    @JoinColumn(name = "role_id",referencedColumnName = "id",nullable = false,columnDefinition = "int default 1")
    private Role role;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, optional = false)
    private Customer customer;
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY,optional = false)
    private Employee employee;

    public Account() {
    }

    public Account(Integer id, String email, String password, Role role, Customer customer, Employee employee) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.customer = customer;
        this.employee = employee;
    }

    public Account(String email, String password, Role role, Customer customer, Employee employee) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.customer = customer;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
