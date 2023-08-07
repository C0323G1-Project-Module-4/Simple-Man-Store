package com.simple_man_store.order_detail.model;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.product.model.Product;

import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double price;
    private int quantity;
    @Column(nullable = false)
    private String size;
    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id",nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id",nullable = false)
    private Product product;

    public OrderDetail(double price, int quantity, String size, Order order, Product product) {
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.order = order;
        this.product = product;
    }


    public OrderDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
