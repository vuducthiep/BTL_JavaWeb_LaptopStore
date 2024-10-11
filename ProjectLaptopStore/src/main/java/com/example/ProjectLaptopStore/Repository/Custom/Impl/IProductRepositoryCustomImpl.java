package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.Product_CreateProductDTO;
import com.example.ProjectLaptopStore.DTO.Product_FindTopPurchasedProductsDTO;
import com.example.ProjectLaptopStore.DTO.Product_UpdateProductDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.Custom.IProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//sử dụng JDBC để lấy dữ liệu
@Repository
@Transactional
public class IProductRepositoryCustomImpl implements IProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Product_FindTopPurchasedProductsDTO> findAllProductsWithTotalQuantityOrdered() {
//        String query = "SELECT p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, p.Description, " +
//                "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered " +
//                "FROM Products p " +
//                "LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID " +
//                "GROUP BY p.ProductID " +
//                "ORDER BY quantityOrdered DESC";
//                Query nativeQuery = entityManager.createNativeQuery(query, Product_FindTopPurchasedProductsDTO.class);
//        return nativeQuery.getResultList();

        //set tay cho toàn bộ trường của lớp DTO vì gặp lỗi 500, ko map dữ liệu được
        String query = "SELECT p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, p.Description, " +
                "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered " +
                "FROM Products p " +
                "LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID " +
                "GROUP BY p.ProductID " +
                "ORDER BY quantityOrdered DESC";
        Query nativeQuery = entityManager.createNativeQuery(query);// ko cần truyền DTO.class
        List<Object[]> results = nativeQuery.getResultList();

        //set thủ công
        List<Product_FindTopPurchasedProductsDTO> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            Product_FindTopPurchasedProductsDTO dto = new Product_FindTopPurchasedProductsDTO(
                    (String) result[0],  // productName
                    (String) result[1],  // brand
                    (String) result[2],  // model
                    (Float) result[3],   // price
                    (Integer) result[4], // stockQuantity
                    (String) result[5],  // description
                    (Integer) result[6], // warrantyPeriod
                    (String) result[7],  // imageURL
                    ((Number) result[8]).longValue()  // quantityOrdered (SUM result)
            );
            dtoList.add(dto);
        }

        return dtoList;


    }

    @Override
    public void createProduct(Product_CreateProductDTO createProductDTO) {
        ProductsEntity productsEntity = new ProductsEntity();
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        suppliersEntity.setSupplierID(createProductDTO.getSupplierId());
        productsEntity.setSupplier(suppliersEntity);
        productsEntity.setProductName(createProductDTO.getProductName());
        productsEntity.setBrand(createProductDTO.getProductBrand());
        productsEntity.setModel(createProductDTO.getModel());
        productsEntity.setPrice(createProductDTO.getPrice());
        productsEntity.setStockQuantity(createProductDTO.getStockQuantity());
//        productsEntity.setDescription(createProductDTO.getDescription());
        productsEntity.setWarrantyPeriod(createProductDTO.getWarrantyPeriod());
        productsEntity.setReleaseDate(createProductDTO.getReleaseDate());
        productsEntity.setImageURL(createProductDTO.getImageUrl());
        entityManager.persist(productsEntity);
    }

    @Override
    public ProductsEntity updateProduct(Product_UpdateProductDTO updateProductDTO, ProductsEntity productsEntityById) {
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        suppliersEntity.setSupplierID(updateProductDTO.getSupplierId());
        productsEntityById.setSupplier(suppliersEntity);
        productsEntityById.setProductName(updateProductDTO.getProductName());
        productsEntityById.setBrand(updateProductDTO.getProductBrand());
        productsEntityById.setModel(updateProductDTO.getModel());
        productsEntityById.setPrice(updateProductDTO.getPrice());
        productsEntityById.setStockQuantity(updateProductDTO.getStockQuantity());
   //     productsEntityById.setDescription(updateProductDTO.getDescription());
        productsEntityById.setWarrantyPeriod(updateProductDTO.getWarrantyPeriod());
        productsEntityById.setReleaseDate(updateProductDTO.getReleaseDate());
        productsEntityById.setImageURL(updateProductDTO.getImageUrl());
        return productsEntityById;
    }


}
