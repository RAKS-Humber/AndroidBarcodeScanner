package com.humber.barcodepos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order  implements Serializable {
    public Order() {
        this.order = new ArrayList<Product>();
        this.subTotal = 0.00;
        this.taxableSubTotal = 0.00;
        Tax = 1.13;
        Total = 0.00;
    }

    public List<Product> getOrder() {
        return order;
    }

    public void setOrder(List<Product> order) {
        this.order = order;
    }
    public void addProduct(Product product) {
        this.order.add(product);
    }

    public List<Product> order;
    public double subTotal;

    public double getTaxableSubTotal() {
        return taxableSubTotal;
    }

    public void setTaxableSubTotal(double taxableSubTotal) {
        this.taxableSubTotal = taxableSubTotal;
    }

    public double taxableSubTotal;
    public double Tax;
    public double Total;

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
