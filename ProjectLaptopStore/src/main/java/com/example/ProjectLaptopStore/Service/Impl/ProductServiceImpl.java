package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.ProductSearchBuilderConverter;
import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Repository.IProductRepository;
import com.example.ProjectLaptopStore.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ProductSearchBuilderConverter productSearchBuilderConverter;

    @Override
    public List<Product_FindTopPurchasedProductsDTO> findTopPurchasedProductAtService() {
        List<Product_FindTopPurchasedProductsDTO> result = productRepository.findAllProductsWithTotalQuantityOrdered();
        return result;
    }

    @Override
    public void createNewProduct(Product_CreateProductDTO createProductDTO) {
        productRepository.createProduct(createProductDTO);
    }

    @Override
    public void updateProduct(Product_UpdateProductDTO updateProductDTO) {
        ProductsEntity productsEntity = productRepository.findById(updateProductDTO.getProductId()).get();
        productRepository.updateProduct(updateProductDTO, productsEntity);
    }

    @Override
    public void deleteProduct(Long[] ids) {
        productRepository.deleteByProductIDIn(ids);
    }


}
