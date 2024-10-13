package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product_DisplayForHomePageDTO {
    private String productName;
    private Float productPrice;
    private String imageURL;

    public Product_DisplayForHomePageDTO() {
    }

    public Product_DisplayForHomePageDTO(String productName, Float productPrice, String imageURL) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageURL = imageURL;
    }
}
