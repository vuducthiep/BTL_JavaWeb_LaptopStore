package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.ProductDescriptionDTO;
import com.example.ProjectLaptopStore.Service.IProductService;
import com.example.ProjectLaptopStore.Service.Impl.ProductDescriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    private ProductDescriptionServiceImpl productDescriptionService;

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/product/productdescription/")
    public List<ProductDescriptionDTO> ProductDescription(){
        List<ProductDescriptionDTO> result = productDescriptionService.finAllProductDescription();
        return result;
    }
    @GetMapping(value = "/may-tinh-xach-tay/{productID}")
    public ProductDescriptionDTO productDescription(@PathVariable Long productID){
        ProductDescriptionDTO result = productDescriptionService.finProductDescriptionById(productID);
        return result;
    }

}
