package com.example.stream.eb.main.sort.content;

/**
 * Created by StReaM on 8/27/2017.
 */

public class SectionContentEntity {

    private int productId = 0;

    private String productName = null;

    private String productThumb = null;

    public String getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
