package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.SuppliersConverter;
import com.example.ProjectLaptopStore.DTO.Supplier_CreateSupplierDTO;
import com.example.ProjectLaptopStore.DTO.SuppliersDTO;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.ISuppliersRepository;
import com.example.ProjectLaptopStore.Service.ISuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class SuppliersServiceImpl implements ISuppliersService {
    @Autowired
    private ISuppliersRepository suppliersRepository;
    @Autowired
    private SuppliersConverter suppliersConverter;
    @Override
    public List<SuppliersDTO> listTopSupplier() {

        List<SuppliersEntity> suppliersEntities = suppliersRepository.findAll();
        List<SuppliersDTO> result = new ArrayList<>();
        for (SuppliersEntity item : suppliersEntities) {
            SuppliersDTO suppliersDTO = suppliersConverter.toSuppliersDTO(item);
            result.add(suppliersDTO);
        }
        return result;
    }

    @Override
    public void createSupplier(Supplier_CreateSupplierDTO creatSuppliers) {
        suppliersRepository.createSupplier(creatSuppliers);
    }

    @Override
    public void deleteSupplier(Long[] ids) {
        suppliersRepository.deleteBySupplierIDIn(ids);
    }
}
