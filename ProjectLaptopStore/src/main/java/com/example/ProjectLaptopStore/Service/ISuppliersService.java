package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.SuppliersDTO;

import java.util.List;

public interface ISuppliersService {
    List<SuppliersDTO> listTopSupplier();
    void createSupplier(Supplier_CreateSupplierDTO creatSuppliers);
}
