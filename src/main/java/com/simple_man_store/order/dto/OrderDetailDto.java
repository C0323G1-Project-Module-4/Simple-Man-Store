package com.simple_man_store.order.dto;

import com.sun.source.doctree.SerialDataTree;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderDetailDto implements Serializable {
    private String size;
    private int quantity;

    public OrderDetailDto(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
