package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.DTO.ProductDetailDTO;
import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Repository.Custom.IProductRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
//sử dụng spring data jpa, và custom để lấy dữ liệu
public interface IProductRepository extends JpaRepository<ProductsEntity,Integer>, IProductRepositoryCustom {
    List<ProductsEntity> findByProductNameContainingAndBrandContaining(String productName, String brand);
    List<ProductsEntity> findByProductNameContainingAndSupplier_SupplierNameContaining(String productName, String supplierName);
    void deleteByProductIDIn(Long[] ids);
    List<ProductsEntity> findTop30ByOrderByReleaseDateDesc();
    List<ProductsEntity> findByProductIDIn(Long[] ids);

}
