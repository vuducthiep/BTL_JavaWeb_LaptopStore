package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
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
}
