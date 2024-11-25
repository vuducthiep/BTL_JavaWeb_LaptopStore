package com.example.ProjectLaptopStore.Service.Impl;


import com.example.ProjectLaptopStore.DTO.CartDetailDTO;
import com.example.ProjectLaptopStore.Entity.CartDetailsEntity;
import com.example.ProjectLaptopStore.Repository.CartDetailRepository;
import com.example.ProjectLaptopStore.Service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    CartDetailRepository cartDetailRepository;


    @Override
    public List<CartDetailDTO> getAllCartDetails(int cartID) {
        List<Object[]> objects = cartDetailRepository.getListCartDetail(cartID);
        List<CartDetailDTO> rs = new ArrayList<>();
        for (Object[] o : objects) {
            CartDetailDTO dto = CartDetailDTO.builder()
                    .cartDetailID((int)o[0])
                    .productId((int) o[1])
                    .productName((String) o[2])
                    .price((BigDecimal) o[3])
                    .quantity((int) o[4])
                    .lineTotal((BigDecimal) o[5])
                    .build();
            rs.add(dto);
        }
        return rs;
    }

    @Override
    public void deleteCartDetail(int cartDetailID) {
        CartDetailsEntity cartDetailsEntity = cartDetailRepository.findById(cartDetailID).orElse(null);
        if (cartDetailsEntity != null) {
            cartDetailRepository.delete(cartDetailsEntity);
        }
        else{
            try {
                throw new Exception("cart detail not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
