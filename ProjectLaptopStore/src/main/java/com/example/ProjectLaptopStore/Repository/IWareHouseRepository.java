package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IWareHouseRepository extends JpaRepository<WareHouseEntity,Integer> {
    WareHouseEntity findByWarehouseID(Integer warehouseID);
}
