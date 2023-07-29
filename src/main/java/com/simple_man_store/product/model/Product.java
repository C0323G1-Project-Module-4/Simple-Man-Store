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
    @Column(columnDefinition = "text")
    private String image;
    private String description;
    @Column(columnDefinition = "bit(1) default true")
    private boolean flag;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetailSet;
    @OneToMany(mappedBy = "product")
    private Set<Warehouse> warehouseSet;

    public Product() {
    }

    public Product(Integer id, String name, double price, String image, String description, boolean flag, Category category, Set<OrderDetail> orderDetailSet, Set<Warehouse> warehouseSet) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.flag = flag;
        this.category = category;
        this.orderDetailSet = orderDetailSet;
        this.warehouseSet = warehouseSet;
    }

    public Product(String name, double price, String image, String description, boolean flag, Category category, Set<OrderDetail> orderDetailSet, Set<Warehouse> warehouseSet) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.flag = flag;
        this.category = category;
        this.orderDetailSet = orderDetailSet;
        this.warehouseSet = warehouseSet;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }

    public Set<Warehouse> getWarehouseSet() {
        return warehouseSet;
    }

    public void setWarehouseSet(Set<Warehouse> warehouseSet) {
        this.warehouseSet = warehouseSet;
    }
}
