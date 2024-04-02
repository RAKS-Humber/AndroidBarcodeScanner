package com.humber.barcodepos.models;

import java.util.List;

public class Order {
    public List<Product> getOrder() {
        return order;
    }

    public void setOrder(List<Product> order) {
        this.order = order;
    }
    public void addProduct(Product product) {
        this.order.add(product);
    }

    private List<Product> order;
}
