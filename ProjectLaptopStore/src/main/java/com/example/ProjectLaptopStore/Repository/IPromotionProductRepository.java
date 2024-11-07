package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.PromotionProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface IPromotionProductRepository extends JpaRepository<PromotionProductEntity,Integer> {
    @Query(value = "SELECT pp.PromotionProductID, pp.ProductID, pp.PromotionID FROM PromotionProduct pp " +
            "JOIN Products p on p.ProductID = pp.ProductID " +
            "JOIN Promotions pm on pm.PromotionID = pp.PromotionID " +
            "WHERE p.ProductID = :productID AND pm.PromotionID = :promotionID",nativeQuery = true)
    PromotionProductEntity getPromotionProductByProductIDAndPromotionID(@Param("productID") int productID,
                                                                        @Param("promotionID") int promotionID);
}
