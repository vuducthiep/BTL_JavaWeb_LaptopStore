package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.ProductInWarehouseEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ProductsInWarehouseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductsInWarehouseRepository extends JpaRepository<ProductInWarehouseEntity,Integer>, ProductsInWarehouseRepositoryCustom {
    @Query(value = "SELECT SUM(Quantity) as sumQuantity FROM ProductsInWarehouse", nativeQuery = true)
    Integer getTotalQuantity();
    @Query(value = "SELECT COUNT(*) FROM ProductsInWarehouse WHERE Quantity > MaxStockLevel ", nativeQuery = true)
    Integer countProductsMaxStockLevel();
    @Query(value = "SELECT COUNT(*) FROM ProductsInWarehouse WHERE Quantity < MinStockLevel", nativeQuery = true)
    Integer countProductsMinStockLevel();
}
