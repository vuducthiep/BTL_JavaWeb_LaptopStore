package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.Enum.Status_Enum;
import com.example.ProjectLaptopStore.Entity.WareHouseEntity;
import com.example.ProjectLaptopStore.Repository.Custom.WareHouseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IWareHouseRepository extends JpaRepository<WareHouseEntity,Integer> , WareHouseRepositoryCustom {
    WareHouseEntity findByWarehouseID(Integer warehouseID);
    List<WareHouseEntity> findByStatus(Status_Enum status);
}
