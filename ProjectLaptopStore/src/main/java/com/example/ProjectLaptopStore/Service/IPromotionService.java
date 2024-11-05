package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;

import java.util.List;

public interface IPromotionService {
    List<Promotions_DisplayPromotionsDTO> getPromotions();
    List<Promotions_DisplayPromotionsDTO> searchPromotion(String promotionName);
}
