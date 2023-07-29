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
    @JoinColumn(name = "product_id",referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id",referencedColumnName = "id",nullable = false)
    private Size size;

    public Warehouse() {
    }

    public Warehouse(Integer id, Integer quantity, Product product, Size size) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.size = size;
    }

    public Warehouse(Integer quantity, Product product, Size size) {
        this.quantity = quantity;
        this.product = product;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
