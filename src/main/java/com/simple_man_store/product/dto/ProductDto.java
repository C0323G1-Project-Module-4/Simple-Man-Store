package com.simple_man_store.product.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProductDto implements Validator {
    private Integer id;
    private String name;
    private double price;
    private String image;
    private String description;
    private int categoryId;
    private int sizeId;
    private int quantity;

    public ProductDto() {
    }

    public ProductDto(Integer id, String name, double price, String image, String description, int categoryId, int sizeId, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.categoryId = categoryId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public ProductDto(String name, double price, String image, String description, int categoryId, int sizeId, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.categoryId = categoryId;
        this.sizeId = sizeId;
        this.quantity = quantity;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDto productDto = (ProductDto) target;
        if (productDto.getName().equals("")){
            errors.rejectValue("name",null,"Không để trống tên sản phẩm");
        } else if (productDto.getName().length()>50) {
            errors.rejectValue("name",null,"Tên không được dài quá 50 kí tự");
        }
        if (productDto.getDescription().equals("")){
            errors.rejectValue("description",null,"Không để trống mô tả sản phẩm");
        } else if (productDto.getDescription().length()>250) {
            errors.rejectValue("description",null,"Không để trống mô tả sản phẩm");
        }

    }
}
