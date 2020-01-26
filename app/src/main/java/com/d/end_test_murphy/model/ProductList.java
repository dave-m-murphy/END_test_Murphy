package com.d.end_test_murphy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// model class for the API response
public class ProductList {
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("product_count")
    @Expose
    private Integer productCount;

    public List<Product> getProducts() {
        return products;
    }

    public String getTitle() {
        return title;
    }

    public Integer getProductCount() {
        return productCount;
    }
}
