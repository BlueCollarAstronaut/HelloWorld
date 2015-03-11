package com.example.sstewart.helloworld;

/**
 * Created by sstewart on 3/11/2015.
 */
public class Product {

    private int _id;
    private String _productName;
    private int _quantity;

    public Product() {

    }

    public Product(int id, String productName, int quantity) {
        this._id = id;
        this._productName = productName;
        this._quantity = quantity;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getId() {
        return this._id;
    }

    public void setProductName(String productName) {
        this._productName = productName;
    }

    public String getProductName() {
        return this._productName;
    }

    public void setQuantity(int quantity) {
        this._quantity = quantity;
    }

    public int getQuantity() {
        return this._quantity;
    }
}
