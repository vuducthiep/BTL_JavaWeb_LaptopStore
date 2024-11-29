package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.OrderDTO;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountDTO;
import com.example.ProjectLaptopStore.DTO.Order_InvoiceDetailDTO;
import com.example.ProjectLaptopStore.DTO.Order_ListBillDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    BigDecimal getTotalAmountInMountAtService();
    Integer TotalCustomerInMonthAtService();
    List<Order_ListBillDTO> ListBillAtService();
    List<Order_InvoiceDetailDTO> ListInvoiceDetailAtService();
    List<Order_CountTotalAmountDTO> listCountTotalAmountAtService();
    BigDecimal getTotalAmountOnline();
    BigDecimal getTotalAmountOffline();


    // tao moi don hang
    void createOrder(OrderDTO dto);
}
