package com.example.ProjectLaptopStore.Convert;

import com.example.ProjectLaptopStore.DTO.ForTest_SuppliersDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuppliersConverter {
    @Autowired
    private ModelMapper modelMapper;
    public ForTest_SuppliersDTO toSuppliersDTO(SuppliersEntity suppliersEntity) {
        ForTest_SuppliersDTO suppliersDTO = modelMapper.map(suppliersEntity, ForTest_SuppliersDTO.class);
        suppliersDTO.setSupplierName(suppliersEntity.getSupplierName());
        suppliersDTO.setAddress(suppliersEntity.getAddress());
        suppliersDTO.setPhoneNumber(suppliersEntity.getPhoneNumber());
        suppliersDTO.setEmail(suppliersEntity.getEmail());
        suppliersDTO.setRepresentative(suppliersEntity.getRepresentative());
        suppliersDTO.setWebsite(suppliersEntity.getWebsite());
        return suppliersDTO;
    }
}
