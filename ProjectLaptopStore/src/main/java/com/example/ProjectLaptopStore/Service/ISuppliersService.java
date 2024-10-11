package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.ForTest_SuppliersDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_UpdateSupplierDTO;

import java.util.List;

public interface ISuppliersService {
    List<ForTest_SuppliersDTO> listTopSupplier();
    void createSupplier(Supplier_CreateSupplierDTO creatSuppliers);
    void deleteSupplier(Long[] ids);
    void updateSupplier(Supplier_UpdateSupplierDTO updateSuppliers);
}
