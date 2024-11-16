package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Order_TotalAmountInMonthDTOConverter;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountDTO;
import com.example.ProjectLaptopStore.DTO.Order_InvoiceDetailDTO;
import com.example.ProjectLaptopStore.DTO.Order_ListBillDTO;
import com.example.ProjectLaptopStore.Repository.OrderRepository;
import com.example.ProjectLaptopStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Order_TotalAmountInMonthDTOConverter order_TotalAmountInMonthDTOConverter;

    @Autowired
    private ProductService productService;


    @Autowired
    private CustomerService customerService;

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public BigDecimal getTotalAmountInMountAtService() {
        BigDecimal res = orderRepository.getTotalAmount();
        return res;
    }

    @Override
    public Integer TotalCustomerInMonthAtService() {
        Integer totalCustomer = orderRepository.countCustomersForCurrentMonth();
        return totalCustomer;
    }

    @Override
    public List<Order_ListBillDTO> ListBillAtService() {
        List<Order_ListBillDTO> result = orderRepository.listBill();
        return result;
    }

    @Override
    public List<Order_InvoiceDetailDTO> ListInvoiceDetailAtService() {
        List<Order_InvoiceDetailDTO> result = orderRepository.listInvoiceDetail();
        return result;
    }

    @Override
    public List<Order_CountTotalAmountDTO> listCountTotalAmountAtService() {
        List<Order_CountTotalAmountDTO> result = orderRepository.listCountTotalAmount();
        return result;
    }

    @Override
    public BigDecimal getTotalAmountOnline() {
        BigDecimal result = orderRepository.getTotalAmountPayOnline();
        return result;
    }

    @Override
    public BigDecimal getTotalAmountOffline() {
        BigDecimal result = orderRepository.getTotalAmountPayOffline();
        return result;
    }




}
