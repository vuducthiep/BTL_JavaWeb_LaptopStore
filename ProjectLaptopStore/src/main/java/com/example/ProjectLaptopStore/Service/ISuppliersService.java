package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_FindTopSupplierDTO;
import com.example.ProjectLaptopStore.DTO.SupplierDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;

import java.util.List;
import java.util.Map;

public interface ISuppliersService {
    List<Supplier_FindTopSupplierDTO> listTopSupplier();
    void createSupplier(SupplierDTO supplierNew);
    void deleteSupplier(Long[] ids);
    void updateSupplier(SupplierDTO supplierUpdate);
    Map<Integer,String> getSupplierForCheckbox();
    List<SuppliersEntity> getListSupplier();
    SuppliersEntity getSupplierByID(Integer supplierId);

}
