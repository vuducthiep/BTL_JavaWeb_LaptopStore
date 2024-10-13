package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class Product_SearchProductByKeyDTO {
    private String productName;
    private Float productPrice;
    private String imageURL;

    public Product_SearchProductByKeyDTO(String productName, Float productPrice, String imageURL) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageURL = imageURL;
    }
}
