package com.simple_man_store.order.model;

import com.simple_man_store.product.model.Product;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> products = new HashMap<>();

    public Cart() {
    }

    public Cart(Map<Product,Integer> products) {
        this.products = products;
    }

    public Map<Product,Integer> getProducts() {
        return products;
    }

//    private boolean checkItemInCart(Product product){
//        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
//            if(entry.getKey().getId().equals(product.getId())){
//                return true;
//            }
//        }
//        return false;
//    }

    private Map.Entry<Product, Integer> selectItemInCart(Product product){
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if(entry.getKey().getId().equals(product.getId())){
                return entry;
            }
        }
        return null;
    }

    public void addProduct(Product product,int quantity){
        if (products.containsKey(product)){
            products.put(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public Integer countProductQuantity(){
        Integer productQuantity = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            productQuantity += entry.getValue();
        }
        return productQuantity;
    }

    public Integer countItemQuantity(){
        return products.size();
    }

    public Float countTotalPayment(){
        float payment = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            payment += entry.getKey().getPrice() * (float) entry.getValue();
        }
        return payment;
    }

    public void clear() {
        products.clear();
    }

    public void removeProduct(Product product){
        products.remove(product);
        System.out.println("remove");
    }
}
