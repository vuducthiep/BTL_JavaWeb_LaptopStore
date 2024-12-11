package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.ImportReceiptDTO;
import com.example.ProjectLaptopStore.Entity.AdminEntity;
import com.example.ProjectLaptopStore.Entity.ProductInWarehouseEntity;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Entity.WareHouseEntity;
import com.example.ProjectLaptopStore.Repository.*;
import com.example.ProjectLaptopStore.Service.ImportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportReceiptServiceImpl implements ImportReceiptService {

    @Autowired
    private ImportReceiptsRepository importReceiptsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private ProductsInWarehouseRepository productsInWarehouseRepository;
    @Override
    public void importReceipt(ImportReceiptDTO importReceiptDTO) {
        WareHouseEntity wareHouseEntity = wareHouseRepository.findById(importReceiptDTO.getWarehouseId()).get();
        AdminEntity adminEntity = adminRepository.findById(importReceiptDTO.getAdminId()).get();
        ProductsEntity productsEntity = productRepository.findById(importReceiptDTO.getProductId()).get();
        ProductInWarehouseEntity productInWarehouseEntity =  new ProductInWarehouseEntity();
        List<ProductInWarehouseEntity> listProductInWareHouse = productsInWarehouseRepository.findAllByWarehouse_warehouseID(importReceiptDTO.getWarehouseId());
        boolean check = false;
        for(ProductInWarehouseEntity productInWarehouseItem : listProductInWareHouse){
            if(productInWarehouseItem.getProduct().getProductID() == productsEntity.getProductID()){
                importReceiptsRepository.addImportReceipt(importReceiptDTO,wareHouseEntity,adminEntity,productsEntity,productInWarehouseItem,1);
                check = true;
                return;
            }
        }
        if(check == false){
            importReceiptsRepository.addImportReceipt(importReceiptDTO,wareHouseEntity,adminEntity,productsEntity,productInWarehouseEntity,2);
        }

    }
}
