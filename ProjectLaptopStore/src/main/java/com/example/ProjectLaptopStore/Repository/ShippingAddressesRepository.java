package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressesRepository extends JpaRepository<ShippingAddressEntity,Integer> {
    List<ShippingAddressEntity> findAllByAddressIDIn(Long[] ids);
}
