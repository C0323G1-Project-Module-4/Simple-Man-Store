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
    private String phoneNumber;
    private String email;
    private boolean gender;
    private String address;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Employee() {
    }

    public Employee(Integer id, String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag, Account account) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
        this.account = account;
    }

    public Employee(String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag, Account account) {
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
