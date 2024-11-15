package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.Enum.Status_Enum;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ISuppliersRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ISuppliersRepository extends JpaRepository<SuppliersEntity,Integer> , ISuppliersRepositoryCustom {
//    List<SuppliersEntity> findAll();
    List<SuppliersEntity> findBySupplierIDIn(Long[] ids);
//    void deleteBySupplierIDIn(Long[] ids);
    List<SuppliersEntity> findByStatus(Status_Enum status);
}
