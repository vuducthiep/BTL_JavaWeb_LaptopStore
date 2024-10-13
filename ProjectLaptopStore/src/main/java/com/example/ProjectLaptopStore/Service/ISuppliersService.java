package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_UpdateSupplierDTO;

import java.util.List;

public interface ISuppliersService {
    void createSupplier(Supplier_CreateSupplierDTO creatSuppliers);
    void deleteSupplier(Long[] ids);
    void updateSupplier(Supplier_UpdateSupplierDTO updateSuppliers);
}
