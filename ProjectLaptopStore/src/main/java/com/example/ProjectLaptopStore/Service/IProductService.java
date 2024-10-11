package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.*;

import java.util.List;

public interface IProductService {
    List<ForTest_ProductDTO> findAllProducts();
    List<ForTest_ProductDTO> findByNameContainingAndBrandContainingAtService(String productName, String brand);
    List<ForTest_ProductAndSupplierDTO> findByProductNameAndSupplier_SupplierNameAtService(String productName, String supplierName);
    List<Product_FindTopPurchasedProductsDTO> findTopPurchasedProductAtService();
    void createNewProduct(Product_CreateProductDTO createProductDTO);
    void updateProduct(Product_UpdateProductDTO updateProductDTO);
    void deleteProduct(Long[] ids);
}
