package com.example.ProjectLaptopStore.DTO;

public class Customer_CountNewCustomerPerMonthDTO {
    private Integer month;
    private Long customerCount;

    public Customer_CountNewCustomerPerMonthDTO(Integer month, Long customerCount) {
        this.month = month;
        this.customerCount = customerCount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Long customerCount) {
        this.customerCount = customerCount;
    }
}
