package com.example.ProjectLaptopStore.Repository.Custom;

import com.example.ProjectLaptopStore.DTO.ProductDetailDTO;
import com.example.ProjectLaptopStore.DTO.Product_FindTopPurchasedProductsDTO;
import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
import com.example.ProjectLaptopStore.Entity.ProductDescriptionEntity;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product_FindTopPurchasedProductsDTO> findAllProductsWithTotalQuantityOrdered();
    void createProduct(ProductDetailDTO productNew, ProductsEntity productsEntity, ProductDescriptionEntity productDescriptionEntity);
    void updateProduct(ProductDetailDTO updateProductDTO, ProductsEntity productsEntityById,ProductDescriptionEntity productDescriptionEntity);
    List<Product_DisplayForHomePageDTO> findAllProductsByKey(Object key);
    List<ProductDetailDTO> listProductDetail();
    ProductDetailDTO getOneProductDetail(Integer id);
//    Page<Product_DisplayForHomePageDTO> findAllProductsByPage(int pageNo, int pageSize);
}
