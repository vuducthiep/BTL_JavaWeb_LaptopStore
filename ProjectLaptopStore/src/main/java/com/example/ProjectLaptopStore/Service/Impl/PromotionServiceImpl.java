package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;
import com.example.ProjectLaptopStore.Entity.PromotionEntity;
import com.example.ProjectLaptopStore.Exception.PromotionNotFoundException;
import com.example.ProjectLaptopStore.Repository.IPromotionRepository;
import com.example.ProjectLaptopStore.Service.IPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    IPromotionRepository promotionRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<Promotions_DisplayPromotionsDTO> getPromotions() {
        List<PromotionEntity> promotionEntities = promotionRepository.findAll();
        List<Promotions_DisplayPromotionsDTO> promotions = new ArrayList<>();
        for (PromotionEntity promotionEntity : promotionEntities) {
            Promotions_DisplayPromotionsDTO dto = modelMapper.map(promotionEntity, Promotions_DisplayPromotionsDTO.class);
            promotions.add(dto);
        }
        return promotions;
    }

    @Override
    public List<Promotions_DisplayPromotionsDTO> searchPromotion(String promotionName) {
        List<PromotionEntity> promotionEntity = promotionRepository.getPromotionByPromotionName(promotionName);
        List<Promotions_DisplayPromotionsDTO> promotion = new ArrayList<>();
        if (promotionEntity != null) {
            for(PromotionEntity pe : promotionEntity){
                Promotions_DisplayPromotionsDTO dto = modelMapper.map(pe, Promotions_DisplayPromotionsDTO.class);
                promotion.add(dto);
            }
        }
        else try {
            throw new Exception();
        } catch (Exception e) {
            throw new PromotionNotFoundException("Không tìm thấy khuyến mãi");
        }
        return promotion;
    }
}
