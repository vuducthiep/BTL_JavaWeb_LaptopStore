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
}
