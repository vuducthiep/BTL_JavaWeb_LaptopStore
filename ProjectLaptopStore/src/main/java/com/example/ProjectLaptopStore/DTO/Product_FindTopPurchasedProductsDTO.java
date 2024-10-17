package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product_FindTopPurchasedProductsDTO {
    private Integer productId;
    private String productName;
    private String brand;
    private String model;
    private Float price;
    private Integer stockQuantity;
    private Integer warrantyPeriod;
    private String imageURL;
    private Long quantityOrdered;

    public Product_FindTopPurchasedProductsDTO(Integer productId, String productName, String brand, String model, Float price, Integer stockQuantity, Integer warrantyPeriod, String imageURL, Long quantityOrdered) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.warrantyPeriod = warrantyPeriod;
        this.imageURL = imageURL;
        this.quantityOrdered = quantityOrdered;
    }
}
