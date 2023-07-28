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
    @JoinColumn(name = "order_id",referencedColumnName = "id",nullable = false,unique = true)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id",nullable = false,unique = true)
    private Product product;
}
