package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.Promotion_getPromotionProduct;
import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;

import java.util.List;

public interface IPromotionService {
    List<Promotions_DisplayPromotionsDTO> getPromotions();
    List<Promotions_DisplayPromotionsDTO> searchPromotion(String promotionName);
    List<Promotion_getPromotionProduct> displayPromotionProduct(String promotionName);
    void addPromotionProduct(int productID,int promotionID);
    void deletePromotionProduct(int productID,int promotionID);
}
