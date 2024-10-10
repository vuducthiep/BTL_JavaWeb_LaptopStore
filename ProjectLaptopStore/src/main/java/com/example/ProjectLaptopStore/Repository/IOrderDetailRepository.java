package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.OrderDetailEntity;
import com.example.ProjectLaptopStore.Repository.Custom.IOrderDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity,Integer>, IOrderDetailRepositoryCustom {
}
