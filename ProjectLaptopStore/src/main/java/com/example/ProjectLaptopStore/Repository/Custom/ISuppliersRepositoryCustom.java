package com.example.ProjectLaptopStore.Repository.Custom;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_FindTopSupplierDTO;
import com.example.ProjectLaptopStore.DTO.SupplierDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;

import java.util.List;

public interface ISuppliersRepositoryCustom {
    List<Supplier_FindTopSupplierDTO> listTopSuppliers();
    void createSupplier(SupplierDTO supplierNew,SuppliersEntity suppliersEntity);
    void updateSupplier(SupplierDTO supplierUpdate, SuppliersEntity suppliersEntity);
    void deleteSupplier(SuppliersEntity suppliersEntity);
}
