package com.simple_man_store.customer.model;

import com.simple_man_store.account.model.Account;

import javax.persistence.*;

@Entity
@Table(name = "customer")
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
    @Column(columnDefinition = "bit(1) default true")
    private boolean gender = true;
    private String address;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag = true;
    @ManyToOne
    @JoinColumn(name = "customer_type_id", referencedColumnName = "id",nullable = false)
    private CustomerType customerType;
    @OneToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id", nullable = false)
    private Account account;
 public Customer(){

 }

    public Customer(Integer id, String name, String dob, String email, String phone_number, boolean gender, String address, boolean flag, CustomerType customerType, Account account) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
        this.customerType = customerType;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", flag=" + flag +
                ", customerType=" + customerType +
                ", account=" + account +
                '}';
    }


}
