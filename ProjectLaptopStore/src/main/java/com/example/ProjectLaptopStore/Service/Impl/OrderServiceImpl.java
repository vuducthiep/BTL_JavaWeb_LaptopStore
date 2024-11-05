package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Order_TotalAmountInMonthDTOConverter;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountDTO;
import com.example.ProjectLaptopStore.DTO.Order_InvoiceDetailDTO;
import com.example.ProjectLaptopStore.DTO.Order_ListBillDTO;
import com.example.ProjectLaptopStore.Repository.IOrderRepository;
import com.example.ProjectLaptopStore.Response.Admin_BillingResponseDTO;
import com.example.ProjectLaptopStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private Order_TotalAmountInMonthDTOConverter order_TotalAmountInMonthDTOConverter;

    @Autowired
    private IProductService productService;


    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISuppliersService suppliersService;

    @Autowired
    private IOrderDetailService orderDetailService;


    @Override
    public BigDecimal getTotalAmountInMountAtService() {
        BigDecimal res = orderRepository.findTotalAmount();
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
    public Admin_BillingResponseDTO adminBillingAtService() {
        Admin_BillingResponseDTO billingResponseDTO = new Admin_BillingResponseDTO();
        try {
            BigDecimal totalAmountOnline = orderRepository.getTotalAmountPayOnline();
            BigDecimal totalAmountOffline = orderRepository.getTotalAmountPayOffline();
            List<Order_InvoiceDetailDTO> listInvoice = orderRepository.listInvoiceDetail();
            billingResponseDTO.setTotalAmountPayOnline(totalAmountOnline);
            billingResponseDTO.setTotalAmountPayOffline(totalAmountOffline);
            billingResponseDTO.setListInvoiceDetail(listInvoice);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return billingResponseDTO;
    }


}
