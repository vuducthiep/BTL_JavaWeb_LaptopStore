package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.ProductDescriptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductDescriptionService{
    List<ProductDescriptionDTO> finAllProductDescription();
    ProductDescriptionDTO finProductDescriptionById(Long id);

}
