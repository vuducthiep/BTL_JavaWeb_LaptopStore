package com.example.ProjectLaptopStore.Repository.Custom;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_UpdateSupplierDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;

public interface ISuppliersRepositoryCustom {
    void createSupplier(Supplier_CreateSupplierDTO createSupplier);
    void updateSupplier(Supplier_UpdateSupplierDTO updateSupplier, SuppliersEntity suppliersEntity);
}
