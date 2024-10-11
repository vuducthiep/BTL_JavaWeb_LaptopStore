package com.example.ProjectLaptopStore.Convert;

import com.example.ProjectLaptopStore.DTO.ForTest_ProductAndSupplierDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductAndSupplierDTOConverter {
    @Autowired
    private ModelMapper modelMapper;
    public ForTest_ProductAndSupplierDTO toProductAndSupplierDTO(ProductsEntity productsEntity) {
        ForTest_ProductAndSupplierDTO productAndSupplierDTO = modelMapper.map(productsEntity, ForTest_ProductAndSupplierDTO.class);
        productAndSupplierDTO.setProductName(productsEntity.getProductName());
        productAndSupplierDTO.setBrand(productsEntity.getBrand());
        productAndSupplierDTO.setModel(productAndSupplierDTO.getModel());
        productAndSupplierDTO.setPrice(productAndSupplierDTO.getPrice());
        productAndSupplierDTO.setDescription(productAndSupplierDTO.getDescription());
        productAndSupplierDTO.setImageURL(productsEntity.getImageURL());
        return productAndSupplierDTO;
    }
}
