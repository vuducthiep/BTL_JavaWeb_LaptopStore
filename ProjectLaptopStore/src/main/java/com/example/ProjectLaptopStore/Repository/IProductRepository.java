package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Repository.Custom.IProductRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
//sử dụng spring data jpa, và custom để lấy dữ liệu
public interface IProductRepository extends JpaRepository<ProductsEntity,Integer>, IProductRepositoryCustom {
    //lấy tất cả thông tin bằng SDJ trong bảng products
    List<ProductsEntity> findAll();
    List<ProductsEntity> findByProductNameContainingAndBrandContaining(String productName, String brand);
    List<ProductsEntity> findByProductNameContainingAndSupplier_SupplierNameContaining(String productName, String supplierName);
    void deleteByProductIDIn(Long[] ids);
    List<ProductsEntity> findTop30ByOrderByReleaseDateDesc();

//    @Query(value = "select * from  products",countQuery = "select count(*) from product",nativeQuery = true)
//    List<ProductsEntity> getAllProducts(Pageable pageable);

    //Lấy top sản phẩm bán chạy
    //jpql

//    @Query(value = "SELECT  p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, p.Description, " +
//            "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered " +
//            "FROM Products p " +
//            "LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID " +
//            "GROUP BY p.ProductID " +
//            "ORDER BY quantityOrdered DESC", nativeQuery = true)
//    List<Product_FindTopPurchasedProductsDTO> findAllProductsWithTotalQuantityOrdered();
}
