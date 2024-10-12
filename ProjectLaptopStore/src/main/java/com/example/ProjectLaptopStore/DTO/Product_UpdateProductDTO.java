package com.example.ProjectLaptopStore.DTO;

import java.util.Date;

public class Product_UpdateProductDTO {
    private Integer productId;
    private Integer supplierId;
    private String productName;
    private String productBrand;
    private String model;
    private Float price;
    private Integer stockQuantity;
//    private String description;
    private Date releaseDate;
    private Integer warrantyPeriod;
    private String imageUrl;

    public Product_UpdateProductDTO(Integer productId, Integer supplierId, String productName, String productBrand, String model, Float price, Integer stockQuantity, Date releaseDate, Integer warrantyPeriod, String imageUrl) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.model = model;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.releaseDate = releaseDate;
        this.warrantyPeriod = warrantyPeriod;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
