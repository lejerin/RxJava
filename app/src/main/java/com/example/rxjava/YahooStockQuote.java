package com.example.rxjava;

import com.google.gson.annotations.SerializedName;

public class YahooStockQuote {
    @SerializedName("id")
    private int id;

    @SerializedName("product_image")
    private String product_image;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("product_detail")
    private String product_detail;

    @SerializedName("product_price")
    private int product_price;

    @SerializedName("product_stock")
    private int product_stock;

    @SerializedName("product_major_category")
    private String product_major_category;

    @SerializedName("product_minor_category")
    private String product_minor_category;

    @SerializedName("product_mark")
    private String product_mark;

    @SerializedName("product_count")
    private int product_count;

    @SerializedName("product_merchandiser")
    private String product_merchandiser;

    public int getId() {
        return id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public int getProduct_price() {
        return product_price;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public String getProduct_major_category() {
        return product_major_category;
    }

    public String getProduct_minor_category() {
        return product_minor_category;
    }

    public String getProduct_mark() {
        return product_mark;
    }

    public int getProduct_count() {
        return product_count;
    }

    public String getProduct_merchandiser() {
        return product_merchandiser;
    }
}
