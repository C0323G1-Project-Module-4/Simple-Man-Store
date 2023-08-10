package com.simple_man_store.order.dto;

import java.util.Objects;

public class ProductCartDto {
    private Integer id;
    private String name;
    private double price;
    private String image;
    private String size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCartDto that = (ProductCartDto) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, size);
    }

    public ProductCartDto() {
    }

    public ProductCartDto(Integer id, String name, double price, String image, String size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
