package com.example.ProjectLaptopStore.Response;

import com.example.ProjectLaptopStore.DTO.Customer_CountNewCustomerPerMonthDTO;
import com.example.ProjectLaptopStore.DTO.OrderDetail_CountQuantityProductPerMonthDTO;
import com.example.ProjectLaptopStore.DTO.Order_CountTotalAmountPerMonthDTO;

import java.util.List;

//kiểu trả về cho trang chart của admin
public class Admin_ChartResponseDTO {
    List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth;
    List<OrderDetail_CountQuantityProductPerMonthDTO> listCountQuantityProductPerMonth;
    List<Order_CountTotalAmountPerMonthDTO> listCountTotalAmountPerMonth;

    public Admin_ChartResponseDTO(List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth, List<OrderDetail_CountQuantityProductPerMonthDTO> listCountQuantityProductPerMonth, List<Order_CountTotalAmountPerMonthDTO> listCountTotalAmountPerMonth) {
        this.listCountNewCustomerPerMonth = listCountNewCustomerPerMonth;
        this.listCountQuantityProductPerMonth = listCountQuantityProductPerMonth;
        this.listCountTotalAmountPerMonth = listCountTotalAmountPerMonth;
    }

    public List<Customer_CountNewCustomerPerMonthDTO> getListCountNewCustomerPerMonth() {
        return listCountNewCustomerPerMonth;
    }

    public void setListCountNewCustomerPerMonth(List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomerPerMonth) {
        this.listCountNewCustomerPerMonth = listCountNewCustomerPerMonth;
    }

    public List<OrderDetail_CountQuantityProductPerMonthDTO> getListCountQuantityProductPerMonth() {
        return listCountQuantityProductPerMonth;
    }

    public void setListCountQuantityProductPerMonth(List<OrderDetail_CountQuantityProductPerMonthDTO> listCountQuantityProductPerMonth) {
        this.listCountQuantityProductPerMonth = listCountQuantityProductPerMonth;
    }

    public List<Order_CountTotalAmountPerMonthDTO> getListCountTotalAmountPerMonth() {
        return listCountTotalAmountPerMonth;
    }

    public void setListCountTotalAmountPerMonth(List<Order_CountTotalAmountPerMonthDTO> listCountTotalAmountPerMonth) {
        this.listCountTotalAmountPerMonth = listCountTotalAmountPerMonth;
    }
}
