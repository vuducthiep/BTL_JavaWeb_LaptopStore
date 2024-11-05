package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Entity.WareHouseEntity;
import com.example.ProjectLaptopStore.Repository.IWareHouseRepository;
import com.example.ProjectLaptopStore.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private IWareHouseRepository wareHouseRepository;
    @Override
    public List<WareHouseEntity> getListWareHouse() {
        List<WareHouseEntity> listWareHouse = wareHouseRepository.findAll();
        return listWareHouse;
    }


}
