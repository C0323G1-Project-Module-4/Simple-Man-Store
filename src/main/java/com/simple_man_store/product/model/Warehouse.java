package com.simple_man_store.product.model;

import javax.persistence.*;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id", nullable = false,unique = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id",referencedColumnName = "id",nullable = false,unique = true)
    private Size size;
}
