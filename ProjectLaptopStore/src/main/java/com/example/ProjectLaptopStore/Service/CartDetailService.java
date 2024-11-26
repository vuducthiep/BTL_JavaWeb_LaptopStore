package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.CartDetailDTO;

import java.util.List;

public interface CartDetailService {

    //lay ra danh sach cartdetail
    List<CartDetailDTO> getAllCartDetails(int cartID);

    //xoa cartdetail
    void deleteCartDetail(int cartDetailID);

    void additionQuantity(int cartDetailID);

    void subtractionQuantity(int cartDetailID);
}
