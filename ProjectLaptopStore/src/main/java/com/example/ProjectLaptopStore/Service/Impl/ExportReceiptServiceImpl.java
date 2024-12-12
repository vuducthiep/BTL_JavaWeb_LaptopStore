package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.ExportReceiptDTO;
import com.example.ProjectLaptopStore.Entity.AdminEntity;
import com.example.ProjectLaptopStore.Entity.ExportReceiptEntity;
import com.example.ProjectLaptopStore.Entity.ProductInWarehouseEntity;
import com.example.ProjectLaptopStore.Exception.InsufficientProductQuantityException;
import com.example.ProjectLaptopStore.Repository.*;
import com.example.ProjectLaptopStore.Service.ExportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExportReceiptServiceImpl implements ExportReceiptService {
    @Autowired
    private ExportReceiptsRepository exportReceiptsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private ProductsInWarehouseRepository productsInWarehouseRepository;

    @Override
    public void addExportReceipt(ExportReceiptDTO exportReceiptDTO) {
        AdminEntity adminEntity = adminRepository.findById(exportReceiptDTO.getAdminId()).orElseThrow(() -> new NoSuchElementException("Admin not found"));
        ProductInWarehouseEntity productInWareHouse = productsInWarehouseRepository
                .findByProduct_ProductIDAndWarehouse_warehouseID(exportReceiptDTO.getProductId(),
                        exportReceiptDTO.getWarehouseId());
        if(productInWareHouse.getQuantity() < exportReceiptDTO.getQuantity()) {
            throw new InsufficientProductQuantityException("Requested quantity exceeds available stock");
        }
        if(productInWareHouse.getQuantity() > exportReceiptDTO.getQuantity()){
            exportReceiptsRepository.addExportReceipt(adminEntity,exportReceiptDTO,productInWareHouse,1);
        }
        if(productInWareHouse.getQuantity() ==  exportReceiptDTO.getQuantity()){
            exportReceiptsRepository.addExportReceipt(adminEntity,exportReceiptDTO,productInWareHouse,2);
        }
    }
}
