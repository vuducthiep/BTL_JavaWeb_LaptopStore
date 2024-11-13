package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Response.Admin_CustomerResponseDTO;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
//    Integer countCustomers(Map<String, Object> params);
    List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth();
    void deleteCustomerAtService(Long[] ids);
    void createCustomerAtService(CustomerDTO customerNew);
    void updateCustomerAtService(CustomerDTO customerUpdate);
    List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth();
    Integer getNewCustomerCurrentMonth();
    List<CustomerDTO> getListCustomers();
    Admin_CustomerResponseDTO adminCustomer();
}
