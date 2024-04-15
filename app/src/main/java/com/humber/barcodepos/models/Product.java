package com.humber.barcodepos.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String barcode;
    private String name;
    private double price;
    private boolean isTaxable;
    private int quantity;

    public Product() {
    }

    public Product(String barcode, String name, double price, boolean isTaxable, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.isTaxable = isTaxable;
        this.quantity = quantity;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public boolean getTaxable() {
        return isTaxable;
    }

    public void setTaxable(boolean taxable) {
        isTaxable = taxable;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
