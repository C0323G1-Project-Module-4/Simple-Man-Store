package com.simple_man_store.product.model;

import com.simple_man_store.order_detail.model.OrderDetail;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private String image;
    private String size;
    private String description;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false,unique = true)
    private Category category;
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetailSet;
    @OneToMany(mappedBy = "product")
    private Set<Warehouse> warehouseSet;
}
