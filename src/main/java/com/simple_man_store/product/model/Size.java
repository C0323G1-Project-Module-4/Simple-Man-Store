package com.simple_man_store.product.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "size")
    private Set<Warehouse> warehouseSet;

    public Size() {
    }

    public Size(Integer id, String name, Set<Warehouse> warehouseSet) {
        this.id = id;
        this.name = name;
        this.warehouseSet = warehouseSet;
    }

    public Size(String name, Set<Warehouse> warehouseSet) {
        this.name = name;
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

    public Set<Warehouse> getWarehouseSet() {
        return warehouseSet;
    }

    public void setWarehouseSet(Set<Warehouse> warehouseSet) {
        this.warehouseSet = warehouseSet;
    }
}
