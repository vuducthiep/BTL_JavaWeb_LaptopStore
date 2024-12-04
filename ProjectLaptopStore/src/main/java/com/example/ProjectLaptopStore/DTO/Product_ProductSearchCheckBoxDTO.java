package com.example.ProjectLaptopStore.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
public class Product_ProductSearchCheckBoxDTO {
    private Integer idBrand;//id cua product chua brand nay
    private String price;
    private String cpu;
    private Integer ram;
    private String hardDrive;
    private String demand;
    private String screenSize;
}
