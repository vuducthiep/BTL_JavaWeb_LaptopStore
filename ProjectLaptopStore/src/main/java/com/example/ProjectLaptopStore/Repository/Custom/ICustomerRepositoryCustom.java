package com.example.ProjectLaptopStore.Repository.Custom;

import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.CustomerEntity;
import com.example.ProjectLaptopStore.Entity.ShippingAddressEntity;
import com.example.ProjectLaptopStore.Entity.UserEntity;

import java.util.List;


public interface ICustomerRepositoryCustom {
    List<Customer_CountNewCustomerPerMonthDTO> listNewCustomerPerMonth();
//    Integer countNewCustomer(CustomerSearchBuilder customerSearchBuilder); //đếm số người dùng mới
//    List<Customer_FindTopCustomer> findTopCustomer(); //tìm danh sách khách mua nhiều nhất
    void deleteCustomer(List<CustomerEntity> listCustomerDelete);
    void createCustomer(CustomerDTO customerNew);
    void updateCustomer(CustomerDTO customerUpdate, ShippingAddressEntity shippingAddressEntity, CustomerEntity customerEntity, UserEntity userEntity);
    List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth();
    List<CustomerDTO> getListCustomer();
}

