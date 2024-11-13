package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPromotionRepository extends JpaRepository<PromotionEntity,Integer> {
    List<PromotionEntity> getAllBy();

    @Query(value = "SELECT * FROM Promotions p " +
            "WHERE p.PromotionName LIKE %:name%", nativeQuery = true)
    List<PromotionEntity> getPromotionByPromotionName(@Param("name") String name);

    @Query(value = "SELECT p.ProductID, \n" +
            "       p.ProductName AS productName, \n" +
            "       p.Brand AS brand, \n" +
            "       MAX(CASE WHEN pm.PromotionID = :promotionID THEN 1 ELSE 0 END) AS hasPromotion\n" +
            "FROM Products p  \n" +
            "LEFT JOIN promotionproduct pp ON p.ProductID = pp.ProductID\n" +
            "LEFT JOIN promotions pm ON pm.PromotionID = pp.PromotionID\n" +
            "GROUP BY p.ProductID", nativeQuery = true)
    List<Object[]> getPromotionProduct(@Param("promotionID")int promotionID);
}
