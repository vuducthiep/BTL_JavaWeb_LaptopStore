package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.ProductDescriptionDTOConverter;
import com.example.ProjectLaptopStore.DTO.ProductDescriptionDTO;
import com.example.ProjectLaptopStore.Entity.ProductDescriptionEntity;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.IProductDescriptionRepository;
import com.example.ProjectLaptopStore.Service.IProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDescriptionServiceImpl implements IProductDescriptionService {

    @Autowired
    private IProductDescriptionRepository productDescriptionRepository;

    @Autowired
    private ProductDescriptionDTOConverter productDescriptionDTOConverter;

    @Override
    public List<ProductDescriptionDTO> finAllProductDescription() {
        List<ProductDescriptionEntity> productDescriptionEntityList = productDescriptionRepository.findAll();
        List<ProductDescriptionDTO> result = new ArrayList<>();
        for (ProductDescriptionEntity item : productDescriptionEntityList) {
            ProductDescriptionDTO productDescription = productDescriptionDTOConverter.toProductDescriptionDTO(item);
            result.add(productDescription);
        }
        return result;
    }

    @Override
    public ProductDescriptionDTO finProductDescriptionById(Long productID) {
        ProductDescriptionEntity productDescriptionEntity = productDescriptionRepository.findAllByProductId(productID);
        ProductDescriptionDTO result = productDescriptionDTOConverter.toProductDescriptionDTO(productDescriptionEntity);
        return result;
    }



}
