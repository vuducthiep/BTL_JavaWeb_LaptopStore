package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Builder.CustomerSearchBuilder;
import com.example.ProjectLaptopStore.Convert.Customer_CountNewCustomerConverter;
import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.CustomerEntity;
import com.example.ProjectLaptopStore.Entity.ShippingAddressEntity;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Repository.ICustomerRepository;
import com.example.ProjectLaptopStore.Repository.IUserRepository;
import com.example.ProjectLaptopStore.Repository.ShippingAddressesRepository;
import com.example.ProjectLaptopStore.Response.Admin_CustomerResponseDTO;
import com.example.ProjectLaptopStore.Service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private Customer_CountNewCustomerConverter customerCountNewCustomerConverter;

    @Autowired
    private ShippingAddressesRepository shippingAddressesRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth() {
        List<Customer_CountNewCustomerPerMonthDTO> result = customerRepository.listNewCustomerPerMonth();
        return result;
    }

    @Override
    public void deleteCustomerAtService(Long[] ids) {
        List<CustomerEntity> listCustomerDeleteById = customerRepository.findAllByCustomerIDIn(ids);
        customerRepository.deleteCustomer(listCustomerDeleteById);
    }

    @Override
    public void createCustomerAtService(CustomerDTO customerNew) {
        customerRepository.createCustomer(customerNew);
    }

    @Override
    public void updateCustomerAtService(CustomerDTO customerUpdate) {
        CustomerEntity customerEntity = customerRepository.findById(customerUpdate.getCustomerID()).get();
        UserEntity userEntity = userRepository.findById(customerUpdate.getUserID()).get();
        ShippingAddressEntity shippingAddressEntity = shippingAddressesRepository.findById(customerUpdate.getAddressID()).get();
        customerRepository.updateCustomer(customerUpdate,shippingAddressEntity,customerEntity,userEntity);
    }

    @Override
    public List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth() {
        List<Customer_FindTopCustomerInMonthDTO> result = customerRepository.listTopCustomerInMonth();
        return result;
    }

    @Override
    public Integer getNewCustomerCurrentMonth() {
        Integer result = customerRepository.getNewCustomerCurrentMonth();
        return result;
    }

    @Override
    public List<CustomerDTO> getListCustomers() {
        return customerRepository.getListCustomer();
    }

    @Override
    public Admin_CustomerResponseDTO adminCustomer() {
        Admin_CustomerResponseDTO result = new Admin_CustomerResponseDTO();
        try {
            List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth = customerRepository.listNewCustomerPerMonth();
            List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth = customerRepository.listTopCustomerInMonth();
            List<CustomerDTO> listCustomer = customerRepository.getListCustomer();
            result.setCountNewCustomerPerMonth(listCountNewCustomerPerMonth);
            result.setFindTopCustomerInMonth(listTopCustomerInMonth);
            result.setListCustomer(listCustomer);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }




}
