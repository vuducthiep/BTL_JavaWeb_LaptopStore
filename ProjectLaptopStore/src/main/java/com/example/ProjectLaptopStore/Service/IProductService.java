package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Response.Admin_ProductDetailResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_ProductResponseDTO;

import java.util.List;

public interface IProductService {
    List<Product_FindTopPurchasedProductsDTO> findTopPurchasedProductAtService();
    void createNewProduct(ProductDetailDTO productCreate);
    void updateProduct(ProductDetailDTO productUpdate);
    void deleteProduct(Long[] ids);
    List<Product_DisplayForHomePageDTO> listSearchProductByKey(Object key);
    List<Product_DisplayForHomePageDTO> listProductForHomePage();
    List<ProductDetailDTO> listProductDetail();
    Admin_ProductResponseDTO adminProduct();
    ProductDetailDTO getProductById(Integer id);
    Admin_ProductDetailResponseDTO adminProductDetail(Integer idProduct);

}
