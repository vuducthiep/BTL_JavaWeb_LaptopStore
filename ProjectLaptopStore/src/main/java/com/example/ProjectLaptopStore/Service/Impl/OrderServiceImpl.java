package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Order_TotalAmountInMonthDTOConverter;
import com.example.ProjectLaptopStore.DTO.OrderDTO;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountDTO;
import com.example.ProjectLaptopStore.DTO.Order_InvoiceDetailDTO;
import com.example.ProjectLaptopStore.DTO.Order_ListBillDTO;
import com.example.ProjectLaptopStore.Entity.OrdersEntity;
import com.example.ProjectLaptopStore.Repository.OrderRepository;
import com.example.ProjectLaptopStore.Service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private ModelMapper modelMapper;


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

    @Override
    public List<OrderDTO> getListOrderByCustomerID(int customerID) {
        List<OrdersEntity> orders = orderRepository.findByCustomerID(customerID);
        List<OrderDTO> dto = new ArrayList<>();
        for (OrdersEntity order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO = modelMapper.map(order, OrderDTO.class);
            dto.add(orderDTO);
        }
        return dto;
    }
}
