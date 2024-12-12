package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.ExportReceiptDTO;
import com.example.ProjectLaptopStore.Entity.AdminEntity;
import com.example.ProjectLaptopStore.Entity.ExportReceipDetailEntity;
import com.example.ProjectLaptopStore.Entity.ExportReceiptEntity;
import com.example.ProjectLaptopStore.Entity.ProductInWarehouseEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ExportReceiptsRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Repository
public class ExportReceiptsRepositoryCustomImpl implements ExportReceiptsRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addExportReceipt(AdminEntity adminEntity, ExportReceiptDTO exportReceiptDTO, ProductInWarehouseEntity productInWarehouseEntity, Integer task) {
        ExportReceiptEntity exportReceiptEntity = new ExportReceiptEntity();
        ExportReceipDetailEntity exportReceipDetailEntity = new ExportReceipDetailEntity();
        exportReceiptEntity.setAdmin(adminEntity);
        exportReceiptEntity.setWarehouse(productInWarehouseEntity.getWarehouse());
        LocalDate currentDate = LocalDate.now();
        exportReceiptEntity.setExportDate(currentDate);
        exportReceiptEntity.setExporter("Admin");
        exportReceipDetailEntity.setExportReceipt(exportReceiptEntity);
        exportReceipDetailEntity.setProduct(productInWarehouseEntity.getProduct());
        exportReceipDetailEntity.setQuantity(exportReceiptDTO.getQuantity());
        entityManager.persist(exportReceiptEntity);
        entityManager.persist(exportReceipDetailEntity);
        //khong xuat het hang
        if(task == 1){
            productInWarehouseEntity.setQuantity(productInWarehouseEntity.getQuantity() - exportReceiptDTO.getQuantity());
            entityManager.merge(productInWarehouseEntity);
            entityManager.flush();
        }
        //xuat het sach hang
        if(task == 2){
            //phai chuyen qua trang thai managed
            if (!entityManager.contains(productInWarehouseEntity)) {
                productInWarehouseEntity = entityManager.merge(productInWarehouseEntity);
            }
            entityManager.remove(productInWarehouseEntity);
            entityManager.flush();

        }
    }
}
