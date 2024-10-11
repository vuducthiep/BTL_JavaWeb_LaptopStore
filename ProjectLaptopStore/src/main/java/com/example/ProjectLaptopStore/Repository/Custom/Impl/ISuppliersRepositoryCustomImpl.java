package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.Supplier_UpdateSupplierDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ISuppliersRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ISuppliersRepositoryCustomImpl implements ISuppliersRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void createSupplier(Supplier_CreateSupplierDTO createSupplier) {
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        suppliersEntity.setSupplierName(createSupplier.getSupplierName());
        suppliersEntity.setAddress(createSupplier.getAddress());
        suppliersEntity.setPhoneNumber(createSupplier.getPhoneNumber());
        suppliersEntity.setEmail(createSupplier.getEmail());
        suppliersEntity.setTaxCode(createSupplier.getTaxcode());
        suppliersEntity.setWebsite(createSupplier.getWebsite());
        suppliersEntity.setRepresentative(createSupplier.getRepresentative());
        suppliersEntity.setPartnershipStartDate(createSupplier.getPartnershipStartDate());
        entityManager.persist(suppliersEntity);

    }

    @Override
    public void updateSupplier(Supplier_UpdateSupplierDTO updateSupplier,SuppliersEntity suppliersEntity) {
        suppliersEntity.setSupplierName(updateSupplier.getSupplierName());
        suppliersEntity.setAddress(updateSupplier.getAddress());
        suppliersEntity.setPhoneNumber(updateSupplier.getPhoneNumber());
        suppliersEntity.setEmail(updateSupplier.getEmail());
        suppliersEntity.setTaxCode(updateSupplier.getTaxcode());
        suppliersEntity.setWebsite(updateSupplier.getWebsite());
        suppliersEntity.setRepresentative(updateSupplier.getRepresentative());
        suppliersEntity.setPartnershipStartDate(updateSupplier.getPartnershipStartDate());
        entityManager.merge(suppliersEntity);
        entityManager.flush();

    }
}
