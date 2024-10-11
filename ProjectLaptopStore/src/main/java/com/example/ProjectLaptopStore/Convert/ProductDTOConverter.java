package com.example.ProjectLaptopStore.Convert;

import com.example.ProjectLaptopStore.DTO.ForTest_ProductDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOConverter {
    @Autowired
    private ModelMapper modelMapper;
    public ForTest_ProductDTO toProductDTO(ProductsEntity productsEntity) {
        ForTest_ProductDTO productDTO = modelMapper.map(productsEntity, ForTest_ProductDTO.class);
        productDTO.setProductName(productsEntity.getProductName());
        productDTO.setBrand(productsEntity.getBrand());
        productDTO.setPrice(productsEntity.getPrice());
        productDTO.setStockQuantity(productsEntity.getStockQuantity());
        productDTO.setDescription(productsEntity.getDescription());
        productDTO.setReleaseDate(productsEntity.getReleaseDate());
        productDTO.setWarrantyPeriod(productsEntity.getWarrantyPeriod());
        productDTO.setImageURL(productsEntity.getImageURL());
        return productDTO;
    }
}
