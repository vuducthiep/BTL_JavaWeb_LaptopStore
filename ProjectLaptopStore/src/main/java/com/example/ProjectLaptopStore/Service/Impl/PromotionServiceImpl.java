package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.Promotion_getPromotionProductDTO;
import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Entity.PromotionEntity;
import com.example.ProjectLaptopStore.Entity.PromotionProductEntity;
import com.example.ProjectLaptopStore.Exception.PromotionNotFoundException;
import com.example.ProjectLaptopStore.Repository.IProductRepository;
import com.example.ProjectLaptopStore.Repository.IPromotionProductRepository;
import com.example.ProjectLaptopStore.Repository.IPromotionRepository;
import com.example.ProjectLaptopStore.Service.IPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    IPromotionRepository promotionRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IPromotionProductRepository promotionProductRepository;

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

    @Override
    public List<Promotion_getPromotionProductDTO> displayPromotionProduct(String promotionName){
        List<Object[]> rs = promotionRepository.getPromotionProduct(promotionName);
        List<Promotion_getPromotionProductDTO> promotionProduct = new ArrayList<>();
        for (Object[] o : rs){
            Promotion_getPromotionProductDTO pp = Promotion_getPromotionProductDTO.builder()
                    .productName((String) o[0])
                    .brand((String) o[1])
                    .hasPromotion((Integer) o[2])
                    .build();
            promotionProduct.add(pp);
        }
        return promotionProduct;
    }


    @Override
    public void addPromotionProduct(int productID, int promotionID) {
        ProductsEntity product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productID));

        // Tìm Promotion theo ID
        PromotionEntity promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new RuntimeException("Promotion not found with ID: " + promotionID));
        PromotionProductEntity promotionProduct = PromotionProductEntity.builder()
                .product(product)
                .promotion(promotion)
                .build();
        // Lưu Product (tự động lưu vào PromotionProduct)
        promotionProductRepository.save(promotionProduct);
    }

    @Transactional
    @Override
    public void deletePromotionProduct(int productID, int promotionID) {
        ProductsEntity product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productID));

        // Tìm Promotion theo ID
        PromotionEntity promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new RuntimeException("Promotion not found with ID: " + promotionID));
        PromotionProductEntity pp = promotionProductRepository.getPromotionProductByProductIDAndPromotionID(productID,promotionID);
        if (pp != null) {
            // Xóa bản ghi trung gian PromotionProductEntity
            promotionProductRepository.delete(pp);
        } else {
            throw new RuntimeException("PromotionProduct not found");
        }
    }
}
