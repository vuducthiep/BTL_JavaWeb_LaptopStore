package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Order_TotalAmountInMonthDTOConverter;
import com.example.ProjectLaptopStore.DTO.OrderDTO;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountDTO;
import com.example.ProjectLaptopStore.DTO.Order_InvoiceDetailDTO;
import com.example.ProjectLaptopStore.DTO.Order_ListBillDTO;
import com.example.ProjectLaptopStore.Entity.CustomerEntity;
import com.example.ProjectLaptopStore.Entity.Enum.OrderStatus_Enum;
import com.example.ProjectLaptopStore.Entity.OrdersEntity;
import com.example.ProjectLaptopStore.Entity.PayMentMethodsEntity;
import com.example.ProjectLaptopStore.Entity.ShippingAddressEntity;
import com.example.ProjectLaptopStore.Repository.CustomerRepository;
import com.example.ProjectLaptopStore.Repository.OrderRepository;
import com.example.ProjectLaptopStore.Repository.PaymentMethodRepository;
import com.example.ProjectLaptopStore.Repository.ShippingAddressesRepository;
import com.example.ProjectLaptopStore.Service.*;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    EntityManager entityManager;

    @Autowired
    CustomerRepository CustomerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ShippingAddressesRepository shippingAddressesRepository;

    @Autowired
    PaymentMethodRepository PaymentMethodRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

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

    @Override
    public void createOrder(List<OrderDTO> orderDTO,int id) {
        if(orderDTO.size() == 0){
            throw new RuntimeException("Create Order Failed");
        }
        CustomerEntity c = customerRepository.findById(id).orElse(null);
        if(c == null){
            throw new RuntimeException("Create Order Failed");
        }
        for (OrderDTO order : orderDTO) {
            PayMentMethodsEntity pm = paymentMethodRepository.findById(order.getPaymentMethodID()).orElse(null);
            if(pm == null){
                throw new RuntimeException("You must select payment method");
            }
            ShippingAddressEntity sa = shippingAddressesRepository.findById(order.getAddressID()).orElse(null);
            if(sa == null){
                throw new RuntimeException("you must select a shipping address");
            }

            OrdersEntity orderEntity = new OrdersEntity();
            orderEntity.setCustomer(c);
            orderEntity.setOrderDate(new Date());
            orderEntity.setTotalAmount(order.getTotalAmount());
            orderEntity.setShippingFee(order.getShippingFee());
            orderEntity.setOrderStatus(OrderStatus_Enum.Confirmed);
            orderEntity.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
            orderEntity.setActualDeliveryDate(order.getActualDeliveryDate());
            orderEntity.setPayMentMethod(pm);
            orderEntity.setShipAddress(sa);
            entityManager.persist(orderEntity);
        }
    }


}
