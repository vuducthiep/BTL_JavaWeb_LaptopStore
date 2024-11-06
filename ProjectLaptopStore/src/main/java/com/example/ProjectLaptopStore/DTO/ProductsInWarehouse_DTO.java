package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsInWarehouse_DTO {
    private Integer productId;
    private String productName;
    private String brand;
    private String model;
    private Integer stockQuantity;
    private Integer quantity;
    private Integer minStockLevel;
    private Integer maxStockLevel;

    public ProductsInWarehouse_DTO(Integer productId, String productName, String brand, String model, Integer stockQuantity, Integer quantity, Integer minStockLevel, Integer maxStockLevel) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.model = model;
        this.stockQuantity = stockQuantity;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
    }
}
