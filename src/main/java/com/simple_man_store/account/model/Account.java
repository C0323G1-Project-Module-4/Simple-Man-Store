package com.simple_man_store.account.model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "encryted_password", nullable = false)
    private String encrytedPassword;

    @Column(name = "flag",columnDefinition = "bit(1)")
    private boolean flag = true;

    public Account() {
    }

    public Account(Integer id, String name, String email, String phone, String encrytedPassword, boolean flag) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.encrytedPassword = encrytedPassword;
        this.flag = flag;
    }

    public Account(String name, String email, String phone, String encrytedPassword, boolean flag) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.encrytedPassword = encrytedPassword;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
