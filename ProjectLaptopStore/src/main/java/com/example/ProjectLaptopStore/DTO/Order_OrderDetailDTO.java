package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order_OrderDetailDTO {
    private List<OrderDetail_displayForStatusDTO> orderdetail;
}
