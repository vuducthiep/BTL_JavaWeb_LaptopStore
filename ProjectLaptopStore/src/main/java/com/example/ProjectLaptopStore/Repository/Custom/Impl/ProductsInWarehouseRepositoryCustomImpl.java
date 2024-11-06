package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.ProductsInWarehouse_DTO;
import com.example.ProjectLaptopStore.Repository.Custom.ProductsInWarehouseRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
public class ProductsInWarehouseRepositoryCustomImpl implements ProductsInWarehouseRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProductsInWarehouse_DTO> listProductsInWarehouse(Integer warehouseId) {
        String query = "SELECT \n" +
                "\tp.ProductID as productId,\n" +
                "    p.ProductName AS productName, \n" +
                "    p.Brand as brand,\n" +
                "    p.Model as model,\n" +
                "    p.StockQuantity as stockQuantity,\n" +
                "    pi.Quantity AS quantity ,\n" +
                "    pi.MinStockLevel AS min ,   \n" +
                "    pi.MaxStockLevel AS max  \n" +
                "FROM \n" +
                "    ProductsInWarehouse pi\n" +
                "JOIN \n" +
                "    Products p ON pi.ProductID = p.ProductID \n" +
                "WHERE \n" +
                "    pi.WarehouseID = :idWarehouse"; // Sử dụng tham số idWarehouse trong WHERE clause

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("idWarehouse", warehouseId);  // Gán giá trị idWarehouse vào câu truy vấn

        List<Object[]> result = nativeQuery.getResultList();
        List<ProductsInWarehouse_DTO> listProductInWareHouse = new ArrayList<>();
        for(Object[] rowOfResult : result) {
            ProductsInWarehouse_DTO item = new ProductsInWarehouse_DTO(
                    (Integer) rowOfResult[0],
                    (String) rowOfResult[1],
                    (String) rowOfResult[2],
                    (String) rowOfResult[3],
                    (Integer) rowOfResult[4],
                    (Integer) rowOfResult[5],
                    (Integer) rowOfResult[6],
                    (Integer) rowOfResult[7]
            );
            listProductInWareHouse.add(item);
        }
        return listProductInWareHouse;
    }
}
