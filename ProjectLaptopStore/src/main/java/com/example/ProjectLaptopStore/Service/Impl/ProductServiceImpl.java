package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Product_DisplayForHomePageConverter;
import com.example.ProjectLaptopStore.Convert.Product_SearchBuilderConverter;
import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.ProductDescriptionEntity;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Repository.IOrderDetailRepository;
import com.example.ProjectLaptopStore.Repository.IOrderRepository;
import com.example.ProjectLaptopStore.Repository.IProductDescriptionRepository;
import com.example.ProjectLaptopStore.Repository.IProductRepository;
import com.example.ProjectLaptopStore.Response.Admin_ProductResponseDTO;
import com.example.ProjectLaptopStore.Service.IOrderDetailService;
import com.example.ProjectLaptopStore.Service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private Product_SearchBuilderConverter productSearchBuilderConverter;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductDescriptionRepository productDescriptionRepository;
    @Autowired
    private Product_DisplayForHomePageConverter productDisplayForHomePageConverter;
    @Override
    public List<Product_FindTopPurchasedProductsDTO> findTopPurchasedProductAtService() {
        List<Product_FindTopPurchasedProductsDTO> result = productRepository.findAllProductsWithTotalQuantityOrdered();
        return result;
    }

    @Override
    public void createNewProduct(ProductDetailDTO productNew) {
        ProductsEntity productsEntity = new ProductsEntity();
        ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
        productRepository.createProduct(productNew,productsEntity,productDescriptionEntity);
    }

    @Override
    public void updateProduct(ProductDetailDTO productUpdate) {
        ProductsEntity productsEntity = productRepository.findById(productUpdate.getProductId()).get();
        ProductDescriptionEntity productDescriptionEntity = productDescriptionRepository.findById(Long.valueOf(productUpdate.getProductDescriptionId())).get();
        productRepository.updateProduct(productUpdate, productsEntity,productDescriptionEntity);
    }

    @Override
    public void deleteProduct(Long[] ids) {
//        List<ProductsEntity> listProductsEntity = productRepository.findByProductIDIn(ids);
//        for (ProductsEntity product : listProductsEntity) {
//            List<ProductDescriptionEntity> productDescriptionEntities = product.getProductDescriptions();
//        }
//
        productRepository.deleteByProductIDIn(ids);
        productDescriptionRepository.deleteByProduct_ProductIDIn(ids);
    }

    @Override
    public List<Product_DisplayForHomePageDTO> listSearchProductByKey(Object key) {
        List<Product_DisplayForHomePageDTO> result = productRepository.findAllProductsByKey(key);
        return result;
    }

    //hàm lấy sản phẩm ra cho trang chủ nhưng giới hạn trong 30 sản phẩm và sắp xếp theo sản phẩm mới
    @Override
    public List<Product_DisplayForHomePageDTO> listProductForHomePage() {
        List<ProductsEntity> listProduct = productRepository.findTop30ByOrderByReleaseDateDesc();
        List<Product_DisplayForHomePageDTO> result = new ArrayList<>();
        for(ProductsEntity item : listProduct){
            Product_DisplayForHomePageDTO productDisplayForHomePageDTO = productDisplayForHomePageConverter.toDisplayHomePage(item);
            result.add(productDisplayForHomePageDTO);
        }
        return result;
    }

    @Override
    public List<ProductDetailDTO> listProductDetail() {
        return productRepository.listProductDetail();
    }

    @Override
    public Admin_ProductResponseDTO adminProduct() {
        Admin_ProductResponseDTO result = new Admin_ProductResponseDTO();
        try {
            List<OrderDetail_CountQuantityProductPerMonthDTO> quantityProductForChart = orderDetailRepository.listCountQuantityProductPerMonth();
            List<Product_FindTopPurchasedProductsDTO> listTopProductSell = productRepository.findAllProductsWithTotalQuantityOrdered();
            List<ProductDetailDTO> listProduct = listProductDetail();
            result.setQuantityProductForChart(quantityProductForChart);
            result.setListTopProductSell(listTopProductSell);
            result.setListProductDetail(listProduct);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ProductDetailDTO getProductById(Integer id) {
        return productRepository.getOneProductDetail(id);
    }


}
