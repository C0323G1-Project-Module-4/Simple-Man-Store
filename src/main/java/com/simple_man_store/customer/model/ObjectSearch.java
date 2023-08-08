package com.simple_man_store.customer.model;

public class ObjectSearch {
    private String searchName;
    private String customerTypeName;

    public ObjectSearch() {
    }

    public ObjectSearch(String searchName, String customerTypeName) {
        this.searchName = searchName;
        this.customerTypeName = customerTypeName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
}
