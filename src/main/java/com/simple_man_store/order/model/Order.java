package com.simple_man_store.order.model;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.order_detail.model.OrderDetail;

import javax.persistence.*;
import java.time.LocalDate;
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
    private String order_date = String.valueOf(LocalDate.now());
    @Column(columnDefinition = "date")
    private String payment_date;
    @Column(columnDefinition = "bit(1)")
    private boolean flag = true;
    @Column(columnDefinition = "bit(1)")
    private boolean status = false;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id")
    private Account account;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetailSet;

    public Order() {
    }

    public Order(Integer id, String name, String email, String phone_number, String address, String order_date, String payment_date, boolean flag, Account account, Set<OrderDetail> orderDetailSet) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.order_date = order_date;
        this.payment_date = payment_date;
        this.flag = flag;
        this.account = account;
        this.orderDetailSet = orderDetailSet;
    }

    public Order(String name, String email, String phone_number, String address, String order_date, String payment_date, boolean flag, Account account, Set<OrderDetail> orderDetailSet) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.order_date = order_date;
        this.payment_date = payment_date;
        this.flag = flag;
        this.account = account;
        this.orderDetailSet = orderDetailSet;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
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

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }
}
