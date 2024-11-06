package com.example.ProjectLaptopStore.Repository.Custom;

import com.example.ProjectLaptopStore.DTO.ProductsInWarehouse_DTO;

import java.util.List;

public interface ProductsInWarehouseRepositoryCustom {
    List<ProductsInWarehouse_DTO> listProductsInWarehouse(Integer warehouseId);
}
